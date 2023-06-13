package ru.knastnt.weather.weatherparser.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

@Slf4j
@Component
public class OpenWeatherMapParser implements WeatherParser {
    @Override
    public WeatherDto parseForCity(String city) {
        return null;
    }
}
