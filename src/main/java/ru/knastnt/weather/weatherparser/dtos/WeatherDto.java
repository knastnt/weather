package ru.knastnt.weather.weatherparser.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeatherDto {
    private String city;
    private String country;
    private String weather;
    private String iconName;
    private Integer temperature;
    private String temperatureUnit;
    private String coordinates;
    private String zoneOffset;
    private WindDirection windDirection;
    private Integer windSpeed;
    private Integer windGust;
    private String windUnit;
    private List<TimeWeatherDto> timeWeather = new ArrayList<>();
}
