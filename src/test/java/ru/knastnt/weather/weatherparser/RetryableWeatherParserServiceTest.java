package ru.knastnt.weather.weatherparser;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import ru.knastnt.weather.WeatherApplicationTests;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;
import ru.knastnt.weather.weatherparser.implementations.WeatherParser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

class RetryableWeatherParserServiceTest extends WeatherApplicationTests {
    @Autowired
    private RetryableWeatherParserService service;
    @Autowired
    private WeatherParser weatherParser;

    @Test
    void parseForCity_attemptsExceeded() {
        Mockito.when(weatherParser.parseForCity(any())).thenThrow(new RuntimeException("synthetic ex"));

        assertThatThrownBy(() -> service.parseForCity("some city")).hasMessage("Can't parse weather for city: \"some city\"");
        Mockito.verify(weatherParser, times(3)).parseForCity("some city");
    }

    @Test
    void parseForCity_attemptsSecondSuccess() {
        Mockito.when(weatherParser.parseForCity(any())).thenThrow(new RuntimeException()).thenReturn(new WeatherDto());

        WeatherDto result = service.parseForCity("some city");
        assertThat(result).isNotNull();
        Mockito.verify(weatherParser, times(2)).parseForCity("some city");
    }
}