package com.hejz.studay.service.impl;

import com.hejz.studay.entity.RelayDefinitionCommand;
import com.hejz.studay.repository.RelayDefinitionCommandRepository;
import com.hejz.studay.service.RelayDefinitionCommandService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RelayDefinitionCommandRepository selayRepository;
    @Override
    public List<RelayDefinitionCommand> getByImei(String imei) {
        return selayRepository.getAllByImei(imei);
    }
    @Override
    public RelayDefinitionCommand getById(Long id) {
        RelayDefinitionCommand selay = selayRepository.getById(id);
        return selay;
    }
    @Override
    public RelayDefinitionCommand save(RelayDefinitionCommand selay) {
        return selayRepository.save(selay);
    }
    @Override
    public RelayDefinitionCommand update(RelayDefinitionCommand selay) {
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
