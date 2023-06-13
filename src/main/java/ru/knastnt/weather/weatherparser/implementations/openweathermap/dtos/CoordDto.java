package ru.knastnt.weather.weatherparser.implementations.openweathermap.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoordDto {
    private BigDecimal lon;
    private BigDecimal lat;
}
