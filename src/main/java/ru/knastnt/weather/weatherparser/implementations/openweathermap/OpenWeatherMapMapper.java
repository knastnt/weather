package ru.knastnt.weather.weatherparser.implementations.openweathermap;

import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

@Mapper
public class OpenWeatherMapMapper {
    public WeatherDto map(Forecast forecast) {
        //todo
        return null;
    }
}
