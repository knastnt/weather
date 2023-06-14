package ru.knastnt.weather.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.knastnt.weather.controllers.dtos.GetWeatherRequestDto;
import ru.knastnt.weather.controllers.dtos.GetWeatherResponseDto;
import ru.knastnt.weather.logic.WeatherSupplyService;

import javax.validation.Valid;

@RestController
public class MainRestController {
    @Autowired
    private WeatherSupplyService service;

    @PostMapping("/send-pdf")
    public GetWeatherResponseDto getWeather(@Valid @RequestBody GetWeatherRequestDto request) {
        service.sendWeatherDetails(request);
        return GetWeatherResponseDto.builder()
                .status(GetWeatherResponseDto.Status.SUCCESS)
                .description("Document will send on " + request.getEmail())
                .build();
    }
}
