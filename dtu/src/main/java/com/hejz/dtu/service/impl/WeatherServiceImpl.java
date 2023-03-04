package com.hejz.dtu.service.impl;

import com.hejz.dtu.dto.*;
import com.hejz.dtu.entity.Weather;
import com.hejz.dtu.repository.WeatherRepository;
import com.hejz.dtu.service.WeatherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    @Override
    public Weather save(Weather weather) {
        return weatherRepository.save(weather);
    }

    @Override
    public Weather update(Weather weather) {
        return weatherRepository.save(weather);
    }

    @Override
    public void delete(Long id) {
        weatherRepository.deleteById( id);
    }

    @Override
    public Weather findById(Long id) {
       return weatherRepository.findById( id).orElse(null);
    }

    @Override
    public Page<Weather> findPage(WeatherFindByPageDto dto) {
        Specification<Weather> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.isNotBlank(dto.getCity())) {
                predicates.add(cb.like(root.get("city"), "%"+dto.getCity()+"%"));
            }
            if(dto.getCreateTime()!=null) {
            predicates.add(cb.equal(root.get("createTime"), dto.getCreateTime()));
            }
            if(StringUtils.isNotBlank(dto.getDn1())) {
                predicates.add(cb.like(root.get("dn1"), "%"+dto.getDn1()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getDn2())) {
                predicates.add(cb.like(root.get("dn2"), "%"+dto.getDn2()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getRemarks())) {
                predicates.add(cb.like(root.get("remarks"), "%"+dto.getRemarks()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getTemperature1())) {
                predicates.add(cb.like(root.get("temperature1"), "%"+dto.getTemperature1()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getTemperature2())) {
                predicates.add(cb.like(root.get("temperature2"), "%"+dto.getTemperature2()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getWeather1())) {
                predicates.add(cb.like(root.get("weather1"), "%"+dto.getWeather1()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getWeather2())) {
                predicates.add(cb.like(root.get("weather2"), "%"+dto.getWeather2()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getWindDirection1())) {
                predicates.add(cb.like(root.get("windDirection1"), "%"+dto.getWindDirection1()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getWindDirection2())) {
                predicates.add(cb.like(root.get("windDirection2"), "%"+dto.getWindDirection2()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getWindSpeed1())) {
                predicates.add(cb.like(root.get("windSpeed1"), "%"+dto.getWindSpeed1()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getWindSpeed2())) {
                predicates.add(cb.like(root.get("windSpeed2"), "%"+dto.getWindSpeed2()+"%"));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<Weather> all = weatherRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }

    @Override
    public List<Weather> findAll(WeatherAllDto dto) {
            Specification<Weather> spec= (root, query, cb)-> {
                List<Predicate> predicates = new ArrayList<>();
                if(dto.getId()!=null && dto.getId()!=0) {
                    predicates.add(cb.equal(root.get("Id"), dto.getId()));
                }
                if(StringUtils.isNotBlank(dto.getCity())) {
                    predicates.add(cb.like(root.get("City"), "%"+dto.getCity()+"%"));
                }
                if(dto.getCreateTime()!= null ) {
                    predicates.add(cb.equal(root.get("CreateTime"), dto.getCreateTime()));
                }
                if(StringUtils.isNotBlank(dto.getDn1())) {
                    predicates.add(cb.like(root.get("Dn1"), "%"+dto.getDn1()+"%"));
                }
                if(StringUtils.isNotBlank(dto.getDn2())) {
                    predicates.add(cb.like(root.get("Dn2"), "%"+dto.getDn2()+"%"));
                }
                if(StringUtils.isNotBlank(dto.getRemarks())) {
                    predicates.add(cb.like(root.get("Remarks"), "%"+dto.getRemarks()+"%"));
                }
                if(StringUtils.isNotBlank(dto.getTemperature1())) {
                    predicates.add(cb.like(root.get("Temperature1"), "%"+dto.getTemperature1()+"%"));
                }
                if(StringUtils.isNotBlank(dto.getTemperature2())) {
                    predicates.add(cb.like(root.get("Temperature2"), "%"+dto.getTemperature2()+"%"));
                }
                if(StringUtils.isNotBlank(dto.getWeather1())) {
                    predicates.add(cb.like(root.get("Weather1"), "%"+dto.getWeather1()+"%"));
                }
                if(StringUtils.isNotBlank(dto.getWeather2())) {
                    predicates.add(cb.like(root.get("Weather2"), "%"+dto.getWeather2()+"%"));
                }
                if(StringUtils.isNotBlank(dto.getWindDirection1())) {
                    predicates.add(cb.like(root.get("WindDirection1"), "%"+dto.getWindDirection1()+"%"));
                }
                if(StringUtils.isNotBlank(dto.getWindDirection2())) {
                    predicates.add(cb.like(root.get("WindDirection2"), "%"+dto.getWindDirection2()+"%"));
                }
                if(StringUtils.isNotBlank(dto.getWindSpeed1())) {
                    predicates.add(cb.like(root.get("WindSpeed1"), "%"+dto.getWindSpeed1()+"%"));
                }
                if(StringUtils.isNotBlank(dto.getWindSpeed2())) {
                    predicates.add(cb.like(root.get("WindSpeed2"), "%"+dto.getWindSpeed2()+"%"));
                }
                Predicate[] andPredicate = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(andPredicate));
            };
            List<Weather> all = weatherRepository.findAll(spec);
            return all;
    }

}
