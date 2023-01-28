package com.hejz.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-28 11:45
 * @Description:
 */
@Data
public class TableDemoDto {

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

    private int id;
    private long timestamp;
    private String author;
    private String reviewer;
    private String title;
    private String contentShort;
    private String content;
    private double forecast;
    private int importance;
    private String type;
    private String status;
    private Date displayTime;
    private boolean CommentDisabled;
    private int pageviews;
    private String image_uri;
    private List<String> platforms;

}
