package com.hejz.dtu.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hejz.dtu.common.Constant;
import com.hejz.dtu.common.Result;
import com.hejz.dtu.dto.GetChartDataDto;
import com.hejz.dtu.dto.SensorDataFindByPageDto;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.entity.SensorData;
import com.hejz.dtu.repository.SensorDataRepository;
import com.hejz.dtu.service.SensorDataService;
import com.hejz.dtu.vo.EchartsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SensorDataServiceImpl implements SensorDataService {

    @Autowired
    private SensorDataRepository sensorDataRepository;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public SensorData save(SensorData sensorData) {
        return sensorDataRepository.save(sensorData);
    }

    @Override
    public void delete(Long id) {
        sensorDataRepository.deleteById(id);
    }
    @Override
    public SensorData findById(Long id) {
       return sensorDataRepository.findById( id).orElse(null);
    }

    @Override
    public Page<SensorData> findPage(SensorDataFindByPageDto dto) {
        Specification<SensorData> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            Join<SensorData, DtuInfo> join=root.join("dtuInfo", JoinType.LEFT);
            if(dto.getCreateDate()!=null) {
            predicates.add(cb.equal(root.get("createDate"), dto.getCreateDate()));
            }
            if(StringUtils.isNotBlank(dto.getData())) {
                predicates.add(cb.like(root.get("data"), "%"+dto.getData()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getNames())) {
                predicates.add(cb.like(root.get("names"), "%"+dto.getNames()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getUnits())) {
                predicates.add(cb.like(root.get("units"), "%"+dto.getUnits()+"%"));
            }
            if(dto.getDtuId()!=null && dto.getDtuId()!=0) {
            predicates.add(cb.equal(join.get("id"), dto.getDtuId()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<SensorData> all = sensorDataRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }

    @Override
    public Result<EchartsVo> getChartData(GetChartDataDto dto) {
        //获取一天内的数据——加缓存
        List<SensorData> dataList = new ArrayList<>();
        Object o = redisTemplate.opsForValue().get(Constant.GET_CHART_DATA_KEY + "::" + dto.getDtuId());
        try {
            if (o != null) {
                dataList = objectMapper.readValue(o.toString(), new TypeReference<List<SensorData>>() {
                });
            } else {
                dataList = getSensorData(dto);
                if (dataList.isEmpty()) return Result.ok();
                String s = objectMapper.writeValueAsString(dataList);
                redisTemplate.opsForValue().set(Constant.GET_CHART_DATA_KEY + "::" + dto.getDtuId(), s, 1, TimeUnit.MINUTES);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        EchartsVo vo = getEchartsVo(dto, dataList);
        return Result.ok(vo);
    }

    private List<SensorData> getSensorData(GetChartDataDto dto) {
        Specification<SensorData> sp = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<SensorData, DtuInfo> join = root.join("dtuInfo", JoinType.LEFT);
            if (dto.getDtuId() != null && dto.getDtuId() != 0) {
                predicates.add(cb.equal(join.get("id"), dto.getDtuId()));
            }
            // 获取今天和昨天的日期
            Date today = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date yesterday = calendar.getTime();
            ;
            // 构建查询语句
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                predicates.add(cb.between(root.get("createDate"), formatter.parse(formatter.format(yesterday)), formatter.parse(formatter.format(today))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        List<SensorData> dataList = sensorDataRepository.findAll(sp);
        return dataList;
    }

    private EchartsVo getEchartsVo(GetChartDataDto dto, List<SensorData> data) {
        //每个小时，求平均数
        // 定义时间格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
        Map<String, Double> map = data.stream().collect(Collectors.groupingBy(
                obj -> formatter.format(obj.getCreateDate()), // 根据createDate格式化为小时级别的字符串进行分组
                Collectors.averagingDouble(obj -> Double.parseDouble(obj.getData().split(",")[dto.getOrderNumber()])) // 计算平均数
        ));
        // 输出结果
        List<String> dateList =new ArrayList<>();
        List<String> createDateList =new ArrayList<>();
        // 将Map的entrySet()转换为List
        List<Map.Entry<String, Double>> mapList = new ArrayList<>(map.entrySet());
        // 对List进行排序，按照键的字母顺序进行排序
        Collections.sort(mapList, Comparator.comparing(Map.Entry::getKey));
        // 遍历排好序的List，输出键和值
        for (Map.Entry<String, Double> entry : mapList) {
            dateList.add(String.valueOf(entry.getValue()));
            createDateList.add(entry.getKey());
        }
        EchartsVo vo=new EchartsVo();
        List<EchartsVo.DatasetsBean> list=new ArrayList<>();
        EchartsVo.DatasetsBean datasetsBean=new EchartsVo.DatasetsBean();
        datasetsBean.setData(dateList);
        datasetsBean.setLabel("Data");
        datasetsBean.setBackgroundColor(dto.getBackgroundColor());
        datasetsBean.setType(dto.getType());
        list.add(datasetsBean);
        vo.setDatasets(list);
        vo.setLabels(createDateList);
        //标题
        SensorData sensorData = data.get(0);
        vo.setTitle("一天内每小时平均：" + sensorData.getNames().split(",")[dto.getOrderNumber()] + "(单位：" + sensorData.getUnits().split(",")[dto.getOrderNumber()] + ")");
        return vo;
    }


    @Override
    public Result<SensorData> getLast(Long dtuId) {
        SensorDataFindByPageDto dto=new SensorDataFindByPageDto();
        dto.setSort("-id");
        dto.setPage(1);
        dto.setLimit(1);
        dto.setDtuId(dtuId);
        Page<SensorData> page = this.findPage(dto);
        if(page.get().findFirst()==null)return Result.error(500,"没有传感器数据!");
        return Result.ok(page.get().findFirst().get());
    }
}
