package com.hejz.studay.service.impl;

import com.hejz.studay.entity.Relay;
import com.hejz.studay.repository.RelayRepository;
import com.hejz.studay.service.RelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:55
 * @Description:
 */
@Service
public class RelayServiceImpl implements RelayService {
    @Autowired
    private RelayRepository selayRepository;
    @Override
    public List<Relay> getByImei(String imei) {
        return selayRepository.getAllByImei(imei);
    }
    @Override
    public Relay getById(Long id) {
        Relay selay = selayRepository.getById(id);
        return selay;
    }
    @Override
    public Relay save(Relay selay) {
        selay.setCloseHex(null);
        return selayRepository.save(selay);
    }
    @Override
    public Relay update(Relay selay) {
        return selayRepository.save(selay);
    }
    @Override
    public void delete(Long id) {
         selayRepository.deleteById(id);
    }
    @Override
    public void deleteByImei(String imei) {
        selayRepository.deleteByImei(imei);
    }

}
