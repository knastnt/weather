package ru.knastnt.weather.weatherparser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

@Slf4j
@Service
public class OpenWeatherMapParser implements WeatherParser {
    @Override
    public WeatherDto parseForCity(String city) {
        return null;
    }
}
