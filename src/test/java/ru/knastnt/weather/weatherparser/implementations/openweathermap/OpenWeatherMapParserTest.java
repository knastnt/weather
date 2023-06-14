package ru.knastnt.weather.weatherparser.implementations.openweathermap;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.knastnt.weather.WeatherApplicationTests;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

@Slf4j
class OpenWeatherMapParserTest extends WeatherApplicationTests {
    @Autowired
    private OpenWeatherMapParser openWeatherMapParser;
    @Test
    @Disabled
    void callOriginalApiTest() {
        WeatherDto weatherDto = openWeatherMapParser.parseForCity("комсомольск-на-амуре");
        log.info("{}", weatherDto);
    }
}