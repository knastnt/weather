package ru.knastnt.weather.weatherparser.implementations.openweathermap;

import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import org.springframework.stereotype.Component;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

@Component
public class OpenWeatherMapMapper {
    public WeatherDto map(Weather weather, Forecast forecast) {
        //todo
        return null;
    }
}
