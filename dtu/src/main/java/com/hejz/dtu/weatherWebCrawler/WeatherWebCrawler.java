package com.hejz.dtu.weatherWebCrawler;

import com.hejz.dtu.entity.Weather;
import com.hejz.dtu.repository.WeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 网页天气爬虫-对老版的有用，新版的没有用,旧版可以提取，新版需要付费接口，网页防爬虫
 */
@Component
@Slf4j
public class WeatherWebCrawler {
    
    @Autowired
    WeatherRepository weatherRepository;

    /**
     * 每天8：05，16：05去天气网爬取数据
     * @throws IOException
     */
    @Scheduled(cron = "0 5 8,16 * * ?")
    public void runtime() throws IOException {
       run();
    }
    private void run() {
        //        String cityCode = "101251401"; //城区
//        String cityCode = "101251411"; //冷水滩
        String cityCode = "101251412"; //零陵
//        String cityCode = "101251403"; //东安
        String url = "http://www.weather.com.cn/weather1d/" + cityCode + ".shtml";
        Weather weather = new Weather();
        try {
            Document doc = Jsoup.connect(url).get();
            Element element1 = doc.selectFirst(".crumbs");
            //省
            String province = element1.child(2).text();
            //市
            String city = element1.child(4).text();
            //乡镇
            String township = element1.child(6).text();
            log.info("地点:" + province + city + township);
            weather.setCity(province + city + township);
            log.info("描述:" + doc.select("input#hidden_title").val());
            weather.setRemarks(doc.select("input#hidden_title").val());
            log.info("天气更新时间:" + doc.select("input#update_time").val());
            String dateStr = doc.select("input#fc_24h_internal_update_time").val();
            log.info("时间:" + dateStr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = sdf.parse(dateStr);
            weather.setCreatedTime(date);
            Element element = doc.selectFirst(".t").select("li:nth-child(1)").get(0);
            String s = element.selectFirst("h1").text();
            log.info("日期：" + s);
            weather.setDn1(element.selectFirst("h1").text());
            log.info("天气：" + element.selectFirst(".wea").text());
            weather.setWeather1(element.selectFirst(".wea").text());
            log.info("温度：" + element.selectFirst(".tem").text());
            weather.setTemperature1(element.selectFirst(".tem").text());
            log.info("风向：" + element.selectFirst(".win span").attr("title"));
            weather.setWindDirection1(element.selectFirst(".win span").attr("title"));
            log.info("风速：" + element.selectFirst(".win").text());
            weather.setWindSpeed1(element.selectFirst(".win").text().replaceAll("<","&lt;"));
            s = element.selectFirst("h1").text();
            element = doc.selectFirst(".t").select("li:nth-child(2)").get(0);
            log.info("日期：" + element.selectFirst("h1").text());
            weather.setDn2(element.selectFirst("h1").text());
            log.info("天气：" + element.selectFirst(".wea").text());
            weather.setWeather2(element.selectFirst(".wea").text());
            weather.setWindDirection2(element.selectFirst(".wea").text());
            log.info("温度：" + element.selectFirst(".tem").text());
            weather.setTemperature2(element.selectFirst(".tem").text());
            log.info("风向：" + element.selectFirst(".win span").attr("title"));
            weather.setWindDirection2( element.selectFirst(".win span").attr("title"));
            log.info("风速：" + element.selectFirst(".win").text());
            weather.setWindSpeed2(element.selectFirst(".win").text().replaceAll("<","&lt;"));
            weatherRepository.save(weather);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
