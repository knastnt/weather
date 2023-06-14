package ru.knastnt.weather.weatherparser.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum WindDirection {
    NORTH ("Северный"),
    NORTH_EAST ("Северо-восточный"),
    EAST ("Восточный"),
    SOUTH_EAST ("Юго-восточный"),
    SOUTH ("Южный"),
    SOUTH_WEST ("Юго-западный"),
    WEST ("Западный"),
    NORTH_WEST ("Северо-западный");

    @Getter
    private String description;
}