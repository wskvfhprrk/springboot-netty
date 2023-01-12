package com.hejz.studay.service.impl;

import com.hejz.studay.common.Constant;
import com.hejz.studay.entity.RelayDefinitionCommand;
import com.hejz.studay.repository.RelayDefinitionCommandRepository;
import com.hejz.studay.service.RelayDefinitionCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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

    @Cacheable(value = Constant.RELAY_DEFINITION_COMMAND, key = "#p0")
    @Override
    public List<RelayDefinitionCommand> getByImei(String imei) {
        return relayDefinitionCommandRepository.getAllByImei(imei);
    }

    @Override
    public RelayDefinitionCommand getById(Long id) {
        RelayDefinitionCommand relayDefinitionCommand = relayDefinitionCommandRepository.getById(id);
        return relayDefinitionCommand;
    }

    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND, key = "#result.imei")
    @Override
    public RelayDefinitionCommand save(RelayDefinitionCommand relayDefinitionCommand) {
        return relayDefinitionCommandRepository.save(relayDefinitionCommand);
    }

    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND, key = "#result.imei")
    @Override
    public RelayDefinitionCommand update(RelayDefinitionCommand relayDefinitionCommand) {
        return relayDefinitionCommandRepository.save(relayDefinitionCommand);
    }

    @Override
    public void delete(Long id) {
        RelayDefinitionCommand relayDefinitionCommand = relayDefinitionCommandRepository.getById(id);
        redisTemplate.delete(Constant.RELAY_DEFINITION_COMMAND + "::" + relayDefinitionCommand.getImei());
        relayDefinitionCommandRepository.deleteById(id);
    }

    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND, key = "#p0")
    @Override
    public void deleteByImei(String imei) {
        relayDefinitionCommandRepository.deleteByImei(imei);
    }

}
