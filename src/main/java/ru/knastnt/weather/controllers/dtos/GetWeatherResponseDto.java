package ru.knastnt.weather.controllers.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetWeatherResponseDto {
    private Status status;
    private String description;

    public enum Status {
        SUCCESS, ERROR
    }
}
