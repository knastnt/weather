package ru.knastnt.weather.doc;

import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

public interface DocumentCreationService {
    byte[] createWeatherDocument(WeatherDto weather);
}
