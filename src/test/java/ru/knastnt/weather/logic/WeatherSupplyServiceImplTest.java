package ru.knastnt.weather.logic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.knastnt.weather.WeatherApplicationTests;
import ru.knastnt.weather.controllers.dtos.GetWeatherRequestDto;

import static org.junit.jupiter.api.Assertions.*;

class WeatherSupplyServiceImplTest extends WeatherApplicationTests {
    @Autowired
    private WeatherSupplyService weatherSupplyService;
    @Test
    void sendWeatherDetails() {
        GetWeatherRequestDto getWeatherRequestDto = new GetWeatherRequestDto();
        getWeatherRequestDto.setCity("комсомольск-на-амуре");

        weatherSupplyService.sendWeatherDetails(getWeatherRequestDto);

        System.out.println();
    }
}