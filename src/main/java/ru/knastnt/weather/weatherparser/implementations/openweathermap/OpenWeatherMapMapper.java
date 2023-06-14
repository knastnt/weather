package ru.knastnt.weather.weatherparser.implementations.openweathermap;

import com.github.prominence.openweathermap.api.model.Coordinate;
import com.github.prominence.openweathermap.api.model.Temperature;
import com.github.prominence.openweathermap.api.model.WeatherState;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import com.github.prominence.openweathermap.api.model.weather.Location;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import com.github.prominence.openweathermap.api.model.weather.Wind;
import org.springframework.stereotype.Component;
import ru.knastnt.weather.utils.WindDirectionConvertor;
import ru.knastnt.weather.weatherparser.dtos.TimeWeatherDto;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

import java.time.ZoneId;
import java.time.ZoneOffset;

import static java.util.Optional.ofNullable;

@Component
public class OpenWeatherMapMapper {
    public WeatherDto map(Weather weather, Forecast forecast) {
        WeatherDto res = new WeatherDto();

        res.setCity(ofNullable(weather.getLocation()).map(Location::getName).orElse(null));
        res.setCountry(ofNullable(weather.getLocation()).map(Location::getCountryCode).orElse(null));
        res.setWeather(ofNullable(weather.getWeatherState()).map(WeatherState::getDescription).orElse(null));
        res.setIconName(ofNullable(weather.getWeatherState()).map(WeatherState::getIconId).orElse(null));
        res.setTemperature(ofNullable(weather.getTemperature()).map(Temperature::getValue).map(Math::round).map(Long::intValue).orElse(null));
        res.setTemperatureUnit(ofNullable(weather.getTemperature()).map(Temperature::getUnit).orElse(null));
        res.setCoordinates(ofNullable(weather.getLocation()).map(Location::getCoordinate).map(Coordinate::toString).orElse(null));
        res.setZoneOffset(ofNullable(weather.getLocation()).map(Location::getZoneOffset).map(ZoneOffset::toString).map(s -> s.replace("Z", "")).orElse(null));
        res.setWindDirection(ofNullable(weather.getWind()).map(Wind::getDegrees).map(Double::intValue).map(WindDirectionConvertor::convert).orElse(null));
        res.setWindSpeed(ofNullable(weather.getWind()).map(Wind::getSpeed).map(Math::round).map(Long::intValue).orElse(null));
        res.setWindGust(ofNullable(weather.getWind()).map(Wind::getGust).map(Math::round).map(Long::intValue).orElse(null));
        res.setWindUnit(ofNullable(weather.getWind()).map(Wind::getUnit).map(s -> s.replace("meter/sec", "м/с")).orElse(null));

        for (WeatherForecast f : forecast.getWeatherForecasts()) {
            TimeWeatherDto timeWeatherDto = new TimeWeatherDto();

            timeWeatherDto.setTime(f.getForecastTime()
                    .atZone(ZoneId.systemDefault())
                    .withZoneSameInstant(ofNullable(forecast.getLocation()).map(com.github.prominence.openweathermap.api.model.forecast.Location::getZoneOffset).orElse(ZoneOffset.UTC))
                    .toLocalDateTime());
            timeWeatherDto.setWeather(ofNullable(f.getWeatherState()).map(WeatherState::getDescription).orElse(null));
            timeWeatherDto.setIconName(ofNullable(f.getWeatherState()).map(WeatherState::getIconId).orElse(null));
            timeWeatherDto.setTemperature(ofNullable(f.getTemperature()).map(Temperature::getValue).map(Math::round).map(Long::intValue).orElse(null));
            timeWeatherDto.setWindDirection(ofNullable(f.getWind()).map(com.github.prominence.openweathermap.api.model.forecast.Wind::getDegrees).map(Double::intValue).map(WindDirectionConvertor::convert).orElse(null));
            timeWeatherDto.setWindSpeed(ofNullable(f.getWind()).map(com.github.prominence.openweathermap.api.model.forecast.Wind::getSpeed).map(Math::round).map(Long::intValue).orElse(null));

            res.getTimeWeather().add(timeWeatherDto);
        }

        return res;
    }
}
