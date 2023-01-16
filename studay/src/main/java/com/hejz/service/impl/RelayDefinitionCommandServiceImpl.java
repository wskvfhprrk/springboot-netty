package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.entity.RelayDefinitionCommand;
import com.hejz.repository.RelayDefinitionCommandRepository;
import com.hejz.service.RelayDefinitionCommandService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:55
 * @Description:
 */
@Service
public class RelayDefinitionCommandServiceImpl implements RelayDefinitionCommandService {
    @Autowired
    private RelayDefinitionCommandRepository relayDefinitionCommandRepository;
    @Autowired
    RedisTemplate redisTemplate;

    @Cacheable(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#p0",unless="#result == null")
    @Override
    public List<RelayDefinitionCommand> getByImei(String imei) {
        return relayDefinitionCommandRepository.getAllByImei(imei);
    }

    //    @Cacheable(value = Constant.RELAY_DEFINITION_COMMAND_ID_CACHE_KEY, key = "#p0",unless="#result == null")
    @Override
    public RelayDefinitionCommand getById(Long id) {
        String key = Constant.RELAY_DEFINITION_COMMAND_ID_CACHE_KEY + "::" + id;
        Object o = redisTemplate.opsForValue().get(key);
        if (o == null) {
            RelayDefinitionCommand relayDefinitionCommand = relayDefinitionCommandRepository.getById(id);
            if (relayDefinitionCommand == null) return null;
            RelayDefinitionCommand relayDefinitionCommand1 = new RelayDefinitionCommand();
            BeanUtils.copyProperties(relayDefinitionCommand, relayDefinitionCommand1);
            redisTemplate.opsForValue().set(key, relayDefinitionCommand1, Duration.ofHours(1));
            return relayDefinitionCommand;
        } else {
            return (RelayDefinitionCommand) o;
        }
    }

    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#result.imei")
    @Override
    public RelayDefinitionCommand save(RelayDefinitionCommand relayDefinitionCommand) {
        return relayDefinitionCommandRepository.save(relayDefinitionCommand);
    }

    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#result.imei")
    @Override
    public RelayDefinitionCommand update(RelayDefinitionCommand relayDefinitionCommand) {
        redisTemplate.delete(Constant.RELAY_DEFINITION_COMMAND_ID_CACHE_KEY + "::" + relayDefinitionCommand.getId());
        return relayDefinitionCommandRepository.save(relayDefinitionCommand);
    }

    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#p0")
    @Override
    public void delete(Long id) {
        RelayDefinitionCommand relayDefinitionCommand = relayDefinitionCommandRepository.getById(id);
        redisTemplate.delete(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + relayDefinitionCommand.getImei());
        relayDefinitionCommandRepository.deleteById(id);
    }

    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#p0")
    @Override
    public void deleteByImei(String imei) {
        relayDefinitionCommandRepository.getAllByImei(imei).stream().forEach(r -> {
            redisTemplate.delete(Constant.RELAY_DEFINITION_COMMAND_ID_CACHE_KEY + "::" + r.getId());
        });
        relayDefinitionCommandRepository.deleteByImei(imei);
    }

}
