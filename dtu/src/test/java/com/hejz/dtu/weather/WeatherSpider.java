package com.hejz.dtu.weather;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class WeatherSpider {
    
    public static void main(String[] args) throws IOException {
        String url = "http://www.weather.com.cn/weather1d/101251401.shtml";
        Document doc = Jsoup.connect(url).get();
        
        // 获取当前天气信息
        Element currentWeather = doc.selectFirst(".wea");
        String currentWeatherText = currentWeather.text();
        System.out.println("当前天气: " + currentWeatherText);
        
        // 获取白天天气信息
        Element dayWeather = doc.selectFirst(".wea");
        String dayWeatherText = dayWeather.text();
        System.out.println("白天天气: " + dayWeatherText);
        
        // 获取晚上天气信息
        Elements nightWeather = doc.select(".wea div");
        String nightWeatherText = nightWeather.get(1).text();
        System.out.println("晚上天气: " + nightWeatherText);
        
        // 获取温度信息
        Elements temp = doc.select(".tem span");
        String currentTemp = temp.get(0).text();
        String dayTemp = temp.get(1).text();
        String nightTemp = temp.get(2).text();
        System.out.println("当前温度: " + currentTemp);
        System.out.println("白天温度: " + dayTemp);
        System.out.println("晚上温度: " + nightTemp);
        
        // 获取风向和风速信息
        Elements wind = doc.select(".win i");
        String currentWindDirection = wind.get(0).attr("title");
        String currentWindSpeed = wind.get(1).text();
        String dayWindDirection = wind.get(2).attr("title");
        String dayWindSpeed = wind.get(3).text();
        String nightWindDirection = wind.get(4).attr("title");
        String nightWindSpeed = wind.get(5).text();
        System.out.println("当前风向: " + currentWindDirection);
        System.out.println("当前风速: " + currentWindSpeed);
        System.out.println("白天风向: " + dayWindDirection);
        System.out.println("白天风速: " + dayWindSpeed);
        System.out.println("晚上风向: " + nightWindDirection);
        System.out.println("晚上风速: " + nightWindSpeed);
    }
}
