package com.hejz.studay.service.impl;

import com.hejz.studay.entity.DtuInfo;
import com.hejz.studay.repository.DtuInfoRepository;
import com.hejz.studay.service.DtuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:55
 * @Description:
 */
@Service
public class DtuInfoServiceImpl implements DtuInfoService {
    @Autowired
    private DtuInfoRepository selayRepository;
    @Override
    public DtuInfo getByImei(String imei) {
        return selayRepository.getAllByImei(imei);
    }
    @Override
    public DtuInfo getById(Long id) {
        DtuInfo selay = selayRepository.getById(id);
        return selay;
    }
    @Override
    public DtuInfo save(DtuInfo selay) {
        return selayRepository.save(selay);
    }
    @Override
    public DtuInfo update(DtuInfo selay) {
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
