package ru.knastnt.weather.doc.dto;

import lombok.Data;

@Data
public class WUnit {
    private String time;
    private String icon;
    private Integer temperature;
    private String temperatureUnit;
    private String weather;
    private String windDirection;
    private int windSpeed;
    private String windUnit;
}
