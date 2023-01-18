package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.entity.CommandStatus;
import com.hejz.repository.CommandStatusRepository;
import com.hejz.service.CommandStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:55
 * @Description:
 */
@Service
public class CommandStatusServiceImpl implements CommandStatusService {
    @Autowired
    private CommandStatusRepository commandStatusRepository;
    @Autowired
    RedisTemplate redisTemplate;

    @Cacheable(value = Constant.COMMAND_STATUS_CACHE_KEY, key = "#p0", unless = "#result == null")
    @Override
    public List<CommandStatus> findByImei(String imei) {
        List<CommandStatus> collect = commandStatusRepository.findByImei(imei).stream()
                .filter(commandStatus -> commandStatus.getStatus()).collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        } else {
            return collect;
        }
    }

    @Override
    public CommandStatus findById(Long id) {
        CommandStatus commandStatus = commandStatusRepository.findById(id).orElse(null);
        return commandStatus;
    }

    @CacheEvict(value = Constant.COMMAND_STATUS_CACHE_KEY, key = "#result.imei")
    @Override
    public CommandStatus save(CommandStatus commandStatus) {
        commandStatus.setId(null);
        return commandStatusRepository.save(commandStatus);
    }

    @CacheEvict(value = Constant.COMMAND_STATUS_CACHE_KEY, key = "#result.imei")
    @Override
    public CommandStatus update(CommandStatus commandStatus) {
        return commandStatusRepository.save(commandStatus);
    }

    @Override
    public void delete(Long id) {
        CommandStatus commandStatus = commandStatusRepository.findById(id).orElse(null);
        redisTemplate.delete(Constant.COMMAND_STATUS_CACHE_KEY + "::" + commandStatus.getImei());
        commandStatusRepository.deleteById(id);
    }

    @CacheEvict(value = Constant.COMMAND_STATUS_CACHE_KEY, key = "#p0")
    @Override
    public void deleteByImei(String imei) {
        //查询当前所有状态，历史的不用查询了
        commandStatusRepository.deleteByImei(imei);
    }

}
