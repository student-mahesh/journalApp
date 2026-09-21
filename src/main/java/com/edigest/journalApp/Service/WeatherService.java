package com.edigest.journalApp.Service;

import com.edigest.journalApp.Cache.AppCache;
import com.edigest.journalApp.api.response.WeatherResponse;
import com.edigest.journalApp.constants.placeHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apikey;
    //private static final String API="https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse=redisService.get("weather_of_"+city,WeatherResponse.class);
        if(weatherResponse !=null){
            return weatherResponse;
        }else{
        String FinalAPI=appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(placeHolders.CITY,city).replace(placeHolders.API_KEY,apikey);
        ResponseEntity<WeatherResponse> response=restTemplate.exchange(FinalAPI, HttpMethod.GET,null,WeatherResponse.class);  // hre the restTemplate deserilaisation means convert json into java that is wheatherResponse object
        WeatherResponse body=response.getBody();
if(body!=null){
    redisService.set("weather_of_"+city,body,300l);
}
        return body;
        }
    }
}
