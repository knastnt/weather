package ru.knastnt.weather.controllers.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class GetWeatherRequestDto {
    @NotEmpty
    private String city;
    @Email
    @NotEmpty
    private String email;
}
