package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.common.Result;
import com.hejz.dto.RelayFindByPageDto;
import com.hejz.entity.ChatMsg;
import com.hejz.entity.DtuInfo;
import com.hejz.entity.Relay;
import com.hejz.nettyserver.PushMsgService;
import com.hejz.repository.RelayRepository;
import com.hejz.service.DtuInfoService;
import com.hejz.service.RelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:55
 * @Description:
 */
@Service
public class RelayServiceImpl implements RelayService {
    @Autowired
    private RelayRepository relayRepository;
    @Autowired
    private DtuInfoService dtuInfoService;
    @Autowired
    private PushMsgService pushMsgService;
    @Autowired
    RedisTemplate redisTemplate;

    @Cacheable(value = Constant.RELAY_CACHE_KEY, key = "#p0", unless = "#result == null")
    public List<Relay> findAllByDtuId(Long dtuId) {
        return relayRepository.findAlByDtuId(dtuId);
    }

    @Override
    public Relay findById(Long id) {
        Relay selay = relayRepository.findById(id).orElse(null);
        return selay;
    }

    @CacheEvict(value = Constant.RELAY_CACHE_KEY, key = "#result.dtuId")
    @Override
    public Relay save(Relay selay) {
        selay.setCloseHex(null);
        return relayRepository.save(selay);
    }

    @CacheEvict(value = Constant.RELAY_CACHE_KEY, key = "#result.dtuId")
    @Override
    public Relay update(Relay selay) {
        return relayRepository.save(selay);
    }

    @Override
    public void delete(Long id) {
        //缓存同步
        Relay relay = relayRepository.findById(id).orElse(null);
        DtuInfo dtuInfo = dtuInfoService.findById(relay.getDtuId());
        redisTemplate.delete(Constant.RELAY_CACHE_KEY + "::" + dtuInfo.getId());
        relayRepository.deleteById(id);
    }

    @CacheEvict(value = Constant.RELAY_CACHE_KEY, key = "#p0")
    @Override
    @Transactional
    public void deleteAlByDtuId(Long dtuId) {
        relayRepository.deleteAllByDtuId(dtuId);
    }

    @Override
    public Page<Relay> findPage(RelayFindByPageDto dto) {
        Specification<Relay> sp = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (dto.getDtuId() != null && dto.getDtuId() != 0) {
                predicates.add(cb.equal(root.get("dtuId"), dto.getDtuId()));
            }
            if (dto.getName() != null && dto.getName().length()> 0) {
                predicates.add(cb.equal(root.get("name"), dto.getName()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<Relay> all = relayRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }


    @Override
    public Result closeTheCanopyInManualMode(Long dtuId) {
        //先变手动模式，然后再发命令
        DtuInfo dtuInfo = dtuInfoService.findById(dtuId);
        dtuInfo.setAutomaticAdjustment(false);
        dtuInfoService.update(dtuInfo);
        //发命令
        String msg=null;
        ChatMsg chatMsg=new ChatMsg();
        chatMsg.setDtuId(dtuId);
        chatMsg.setMsg(msg);
        pushMsgService.pushMsgToChannel(chatMsg);
        return Result.ok();
    }

    @Override
    public Result openTheCanopyInManualMode(Long dtuId) {
        //先变手动模式，然后再发命令
        DtuInfo dtuInfo = dtuInfoService.findById(dtuId);
        dtuInfo.setAutomaticAdjustment(false);
        dtuInfoService.update(dtuInfo);
        //发命令
        String msg=null;
        ChatMsg chatMsg=new ChatMsg();
        chatMsg.setDtuId(dtuId);
        chatMsg.setMsg(msg);
        pushMsgService.pushMsgToChannel(chatMsg);
        return Result.ok();
    }
}
