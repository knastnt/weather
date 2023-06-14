package ru.knastnt.weather.weatherparser.implementations.openweathermap;

import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import org.springframework.stereotype.Component;
import ru.knastnt.weather.utils.WindDirectionConvertor;
import ru.knastnt.weather.weatherparser.dtos.TimeWeatherDto;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

@Component
public class OpenWeatherMapMapper {
    public WeatherDto map(Weather weather, Forecast forecast) {
        WeatherDto res = new WeatherDto();

        res.setCity(weather.getLocation().getName());
        res.setCountry(weather.getLocation().getCountryCode());
        res.setWeather(weather.getWeatherState().getDescription());
        res.setIconName(weather.getWeatherState().getIconId());
        res.setTemperature((int) Math.round(weather.getTemperature().getValue()));
        res.setTemperatureUnit(weather.getTemperature().getUnit());
        res.setCoordinates(weather.getLocation().getCoordinate().toString());
        res.setZoneOffset(weather.getLocation().getZoneOffset().toString().replace("Z", ""));
        res.setWindDirection(WindDirectionConvertor.convert(weather.getWind().getDegrees().intValue()));
        res.setWindSpeed((int) Math.round(weather.getWind().getSpeed()));
        res.setWindGust((int) Math.round(weather.getWind().getGust()));
        res.setWindUnit(weather.getWind().getUnit().replace("meter/sec", "м/с"));

        for (WeatherForecast f : forecast.getWeatherForecasts()) {
            TimeWeatherDto timeWeatherDto = new TimeWeatherDto();

            timeWeatherDto.setTime(f.getForecastTime());
            timeWeatherDto.setWeather(f.getWeatherState().getDescription());
            timeWeatherDto.setIconName(f.getWeatherState().getIconId());
            timeWeatherDto.setTemperature((int) Math.round(f.getTemperature().getValue()));
            timeWeatherDto.setWindDirection(WindDirectionConvertor.convert(f.getWind().getDegrees().intValue()));
            timeWeatherDto.setWindSpeed((int) Math.round(f.getWind().getSpeed()));

            res.getTimeWeather().add(timeWeatherDto);
        }

        return res;
    }
}
