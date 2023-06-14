package ru.knastnt.weather.doc.dto;

import lombok.Data;

@Data
public class WUnit {
    private String time;
    private String icon;
    private String temperature;
    private String temperatureUnit;
    private String weather;
    private String windDirection;
    private String windSpeed;
    private String windUnit;
}
