package com.edigest.journalApp.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class WeatherResponse{


    private  Current current;
    @Getter
    @Setter
    public class Current{
        @JsonProperty("observation_time")
        private String observationTime;
        private int temperature;
        private int feelslike;
        private int uv_index;
        private int visibility;

    }
    public class AirQuality{
        private String co;
        private String no2;
        private String o3;
        private String so2;
        private String pm2_5;
        private String pm10;
        private String usEpaIndex;
        private String gbDefraIndex;
    }

    public class Astro{
        private String sunrise;
        private String sunset;
        private String moonrise;
        private String moonset;

    }







}






