package ru.knastnt.weather.weatherparser.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeWeatherDto {
    private LocalDateTime time;
    private String weather;
    private String iconName;
    private Integer temperature;
    private WindDirection windDirection;
    private Integer windSpeed;
}
