package com.hejz.dtu.weather;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * 对老版的有用，新版的没有用,旧版可以提取，新版需要付费接口，网页防爬虫
 */
public class WeatherWebCrawler {
    public static void main(String[] args) {
//        String cityCode = "101251401"; //城区
//        String cityCode = "101251411"; //冷水滩
//        String cityCode = "101251412"; //零陵
        String cityCode = "101251403"; //东安
        String url = "http://www.weather.com.cn/weather1d/" + cityCode + ".shtml";
        try {
            Document doc = Jsoup.connect(url).get();
            Element element1 = doc.selectFirst(".crumbs");
            //省
            String province = element1.child(2).text();
            //市
            String city = element1.child(4).text();
            //乡镇
            String township = element1.child(6).text();
            System.out.println("地点:" + province + city + township);
            System.out.println("日期:" + doc.select("input#hidden_title").val());
            System.out.println("时间:" + doc.select("input#update_time").val());
//            System.out.println("时间:" + doc.select("input#fc_24h_internal_update_time").val());
            //当关
//            doc.selectFirst(".sk mySkyNull").text();
            //当天
            Elements elements = doc.selectFirst(".t").select("li");
            for (Element element : elements) {
                if (element.selectFirst("h1") != null) {
                    String s = element.selectFirst("h1").text();
                    System.out.println("日期：" + s.substring(s.indexOf("日") + 1));
                    System.out.println("天气：" + element.selectFirst(".wea").text());
                    System.out.println("温度：" + element.selectFirst(".tem").text());
                    System.out.println("风向：" + element.selectFirst(".win span").attr("title"));
                    System.out.println("风速：" + element.selectFirst(".win").text());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
