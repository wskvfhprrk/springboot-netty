package com.hejz.controller;

import com.hejz.common.Result;
import com.hejz.dto.EchartsDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-28 10:18
 * @Description: earchar视图数据
 */
@RestController
@RequestMapping("echarts")
@Api(tags = "echarts视图数据集")
public class EchartsController {

    @ApiOperation("首页视图展示——不需要了")
    @GetMapping("list")
    public Result<EchartsDto> homeList(){
        List<EchartsDto.ItemsBean> list=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            /**
             * order_no : 3C55b661-aF5C-FdE2-7Db3-E61Bab9fa20d
             * timestamp : 1389267532334
             * username : Dorothy Taylor
             * price : 3187.7
             * status : success
             */
            String status = i % 2 == 0 ? "success" : "pending";
            list.add(new EchartsDto.ItemsBean(UUID.randomUUID().toString(),System.currentTimeMillis(),"name"+i,Math.random(), status));
        }
        EchartsDto echartsDto = new EchartsDto();
        echartsDto.setTotal(20);
        echartsDto.setItems(list);
        return Result.ok(echartsDto);
    }
}
