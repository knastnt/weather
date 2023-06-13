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
    void parseForCity_doNotRetry() {
        Mockito.doThrow(new CityNotFoundException()).when(weatherParser).parseForCity(any());

        assertThatThrownBy(() -> service.parseForCity("some city")).isInstanceOf(CityNotFoundException.class);
        Mockito.verify(weatherParser, times(1)).parseForCity("some city");
    }

    @Test
    void parseForCity_attemptsExceeded() {
        Mockito.doThrow(new RuntimeException("synthetic ex")).when(weatherParser).parseForCity(any());

        assertThatThrownBy(() -> service.parseForCity("some city")).hasMessage("Can't parse weather for city: \"some city\"");
        Mockito.verify(weatherParser, times(3)).parseForCity("some city");
    }

    @Test
    void parseForCity_attemptsSecondSuccess() {
        Mockito.doThrow(new RuntimeException("synthetic ex")).doReturn(new WeatherDto()).when(weatherParser).parseForCity(any());

        WeatherDto result = service.parseForCity("some city");
        assertThat(result).isNotNull();
        Mockito.verify(weatherParser, times(2)).parseForCity("some city");
    }
}