package ru.knastnt.weather.weatherparser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;
import ru.knastnt.weather.weatherparser.implementations.WeatherParser;

@Slf4j
@Service
public class RetryableWeatherParserService implements WeatherParserService {
    @Autowired
    private WeatherParser openWeatherMapParser;

    @Override
    @Retryable
    public WeatherDto parseForCity(String city) {
        log.debug("Try to parse weather for city: \"{}\"", city);
        return openWeatherMapParser.parseForCity(city);
    }

    @Recover
    WeatherDto logFailAndThrowEx(Exception e, String city) {
        log.warn("Maximum number of attempts for parsing weather exceeded", e);
        throw new RuntimeException("Can't parse weather for city: \"" + city + "\"");
    }
}
