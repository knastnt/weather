package ru.knastnt.weather.logic;

import ru.knastnt.weather.controllers.dtos.GetWeatherRequestDto;

public interface WeatherSupplyService {
    void sendWeatherDetails(GetWeatherRequestDto request);
}
