package ru.knastnt.weather.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.knastnt.weather.controllers.dtos.GetWeatherRequestDto;

@Slf4j
@Service
public class WeatherSupplyServiceImpl implements WeatherSupplyService {
    @Override
    public void sendWeatherDetails(GetWeatherRequestDto request) {

    }
}
