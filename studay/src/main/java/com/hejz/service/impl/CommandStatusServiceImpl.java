package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.entity.CommandStatus;
import com.hejz.entity.DtuInfo;
import com.hejz.repository.CommandStatusRepository;
import com.hejz.service.CommandStatusService;
import com.hejz.service.DtuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private DtuInfoService dtuInfoService;
    @Autowired
    RedisTemplate redisTemplate;

    @Cacheable(value = Constant.COMMAND_STATUS_CACHE_KEY, key = "#p0", unless = "#result == null")
    @Override
    public List<CommandStatus> findAllByDtuId(Long dtuId) {
        List<CommandStatus> collect = commandStatusRepository.findAllByDtuId(dtuId).stream()
                .filter(commandStatus -> commandStatus.getStatus()).collect(Collectors.toList());
        if (collect.isEmpty()) {
            return new ArrayList<>();
        } else {
            return collect;
        }
    }

    @Override
    public CommandStatus findById(Long id) {
        CommandStatus commandStatus = commandStatusRepository.findById(id).orElse(null);
        return commandStatus;
    }

    @CacheEvict(value = Constant.COMMAND_STATUS_CACHE_KEY, key = "#p0.dtuId")
    @Override
    public CommandStatus save(CommandStatus commandStatus) {
        return commandStatusRepository.save(commandStatus);
    }

    @CacheEvict(value = Constant.COMMAND_STATUS_CACHE_KEY, key = "#p0.dtuId")
    @Override
    public CommandStatus update(CommandStatus commandStatus) {
        return commandStatusRepository.save(commandStatus);
    }

    @Override
    public void delete(Long id) {
        CommandStatus commandStatus = commandStatusRepository.findById(id).orElse(null);
        DtuInfo dtuInfo = dtuInfoService.findById(commandStatus.getDtuId());
        redisTemplate.delete(Constant.COMMAND_STATUS_CACHE_KEY + "::" + dtuInfo.getId());
        commandStatusRepository.deleteById(id);
    }


}
