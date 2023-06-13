package ru.knastnt.weather.weatherparser;

import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

public interface WeatherParserService {
    WeatherDto parseForCity(String city);
}
