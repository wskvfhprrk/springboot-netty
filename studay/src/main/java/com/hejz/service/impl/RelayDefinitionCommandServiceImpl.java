package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.entity.DtuInfo;
import com.hejz.entity.RelayDefinitionCommand;
import com.hejz.repository.RelayDefinitionCommandRepository;
import com.hejz.service.DtuInfoService;
import com.hejz.service.RelayDefinitionCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    private DtuInfoService dtuInfoService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Cacheable(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#p0", unless = "#result == null")
    @Override
    public List<RelayDefinitionCommand> findByAllDtuId(Long dtuId) {
        return relayDefinitionCommandRepository.findByDtuId(dtuId);
    }

    @Override
    public RelayDefinitionCommand findById(Long id) {
        return relayDefinitionCommandRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#result.dtuId")
    @Override
    public RelayDefinitionCommand save(RelayDefinitionCommand relayDefinitionCommand) {
        relayDefinitionCommand.setId(null);
        return relayDefinitionCommandRepository.save(relayDefinitionCommand);
    }

    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#result.dtuId")
    @Override
    public RelayDefinitionCommand update(RelayDefinitionCommand relayDefinitionCommand) {
        return relayDefinitionCommandRepository.save(relayDefinitionCommand);
    }

    @Override
    public void delete(Long id) {
        Optional<RelayDefinitionCommand> optionalRelayDefinitionCommand = relayDefinitionCommandRepository.findById(id);
        if (optionalRelayDefinitionCommand.isPresent()) {
            DtuInfo dtuInfo = dtuInfoService.findById(optionalRelayDefinitionCommand.get().getDtuId());
            redisTemplate.delete(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY+"::"+dtuInfo.getId());
            relayDefinitionCommandRepository.deleteById(id);
        }
    }

    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#p0")
    @Override
    @Transactional
    public void deleteAllByDtuId(Long dtuId) {
        relayDefinitionCommandRepository.deleteAllByDtuId(dtuId);
    }

}
