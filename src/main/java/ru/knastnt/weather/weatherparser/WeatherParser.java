package ru.knastnt.weather.weatherparser;

import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

public interface WeatherParser {
    WeatherDto parseForCity(String city);
}
