package ru.knastnt.weather.utils;

import ru.knastnt.weather.weatherparser.dtos.WindDirection;

public class WingDirectionConvertor {
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
