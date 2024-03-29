package ru.knastnt.weather.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.knastnt.weather.controllers.dtos.GetWeatherResponseDto;
import ru.knastnt.weather.weatherparser.CityNotFoundException;

import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GetWeatherResponseDto> catchMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String description = "Request validation errors: " + e.getFieldErrors().stream()
                .sorted(Comparator.comparing(FieldError::getField))
                .map(o -> String.join(": ", o.getField(), o.getDefaultMessage()))
                .collect(Collectors.joining("; "));

        return ResponseEntity.badRequest().body(
            GetWeatherResponseDto.builder()
                .status(GetWeatherResponseDto.Status.ERROR)
                .description(description)
                .build()
        );
    }

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<GetWeatherResponseDto> catchCityNotFoundException(CityNotFoundException e) {
        return ResponseEntity.badRequest().body(
            GetWeatherResponseDto.builder()
                .status(GetWeatherResponseDto.Status.ERROR)
                .description("City not found")
                .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GetWeatherResponseDto> catchException(Exception e) {
        log.error("Exception", e);
        return ResponseEntity.internalServerError().body(
                GetWeatherResponseDto.builder()
                        .status(GetWeatherResponseDto.Status.ERROR)
                        .description(e.getMessage())
                        .build()
        );
    }
}
