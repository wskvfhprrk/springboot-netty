package com.hejz.controller;

import com.hejz.common.Page;
import com.hejz.common.PageResult;
import com.hejz.common.Result;
import com.hejz.dto.TableDemoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-28 11:26
 * @Description: vue-admin前端demo
 */
@RestController
@RequestMapping("demo")
public class VueAdminDemoController {
    @GetMapping("tableList")
    public Result<PageResult> tableDemoList(Page page){
        /**
         * id : 1
         * timestamp : 1580801129531
         * author : Mark
         * reviewer : Sarah
         * title : Qgwui Udhgmzmwq Ngfu Hwnj Niuuyft Ugqyi
         * content_short : mock data
         * content : <p>I am testing data, I am testing data.</p><p><img src="https://wpimg.wallstcn.com/4c69009c-0fd4-4153-b112-6cb53d1cf943"></p>
         * forecast : 64.24
         * importance : 3
         * type : JP
         * status : draft
         * display_time : 1987-08-13 19:22:55
         * comment_disabled : true
         * pageviews : 2832
         * image_uri : https://wpimg.wallstcn.com/e4558086-631c-425c-9430-56ffb46e70b3
         * platforms : ["a-platform"]
         */
        List<TableDemoDto> list=new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            TableDemoDto tableDemoDto=new TableDemoDto();
            tableDemoDto.setId(i);
            tableDemoDto.setTimestamp(System.currentTimeMillis());
            tableDemoDto.setAuthor("Mark"+i);
            tableDemoDto.setReviewer("Qgwui Udhgmzmwq Ngfu Hwnj Niuuyft Ugqyi"+i);
            tableDemoDto.setTitle("Qgwui Udhgmzmwq Ngfu Hwnj Niuuyft Ugqyi"+i);
            tableDemoDto.setContentShort("mock data"+i);
            tableDemoDto.setContent("<p>I am testing data, I am testing data.</p><p><img src=\"https://wpimg.wallstcn.com/4c69009c-0fd4-4153-b112-6cb53d1cf943\"></p>");
            tableDemoDto.setForecast(i);
            tableDemoDto.setImportance(i);
            tableDemoDto.setType("JP");
            tableDemoDto.setStatus("draft");
            tableDemoDto.setDisplayTime(new Date());
            tableDemoDto.setCommentDisabled(true);
            tableDemoDto.setPageviews(2832+i);
            tableDemoDto.setImage_uri("https://wpimg.wallstcn.com/e4558086-631c-425c-9430-56ffb46e70b3");
            tableDemoDto.setPlatforms(Arrays.asList("a-platform"+i));
            list.add(tableDemoDto);
        }
        List<TableDemoDto> list1=new ArrayList<>();
        for (Integer i = 0; i < page.getLimit(); i++) {
            list1.add(list.get(page.getPage()+i));
        }
        PageResult pageResult = new PageResult();
        pageResult.setTotal(100L);
        pageResult.setPage(page.getPage());
        pageResult.setLimit(page.getLimit());
        pageResult.setItems(list1);
        return Result.ok(pageResult);
    }

}
