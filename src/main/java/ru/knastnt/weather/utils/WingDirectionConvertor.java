package ru.knastnt.weather.utils;

import lombok.AllArgsConstructor;

public class WingDirectionConvertor {
    @AllArgsConstructor
    enum WindDirection {
        NORTH ("Северный"),
        NORTH_EAST ("Северо-восточный"),
        EAST ("Восточный"),
        SOUTH_EAST ("Юго-восточный"),
        SOUTH ("Южный"),
        SOUTH_WEST ("Юго-западный"),
        WEST ("Западный"),
        NORTH_WEST ("Северо-западный");

        private String description;
    }


    public static WindDirection convert(Integer degree) {
        final int divisor = 360 / WindDirection.values().length;
        final int coci = degree / divisor;
        final int resto = degree % divisor;
        if (resto <= divisor / 2) {
            return WindDirection.values()[coci % WindDirection.values().length];
        } else {
            return WindDirection.values()[(coci + 1) % WindDirection.values().length];
        }
    }
}
