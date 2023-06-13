package ru.knastnt.weather.weatherparser.implementations.openweathermap;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.exception.NoDataFoundException;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.knastnt.weather.weatherparser.CityNotFoundException;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;
import ru.knastnt.weather.weatherparser.implementations.WeatherParser;

@Slf4j
@Component
public class OpenWeatherMapParser implements WeatherParser {
    @Autowired
    private OpenWeatherMapMapper mapper;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Override
    public WeatherDto parseForCity(String city) {
        try {
            OpenWeatherMapClient openWeatherClient = new OpenWeatherMapClient(apiKey);

            Weather weather = openWeatherClient
                    .currentWeather()
                    .single()
                    .byCityName(city)
                    .language(Language.RUSSIAN)
                    .unitSystem(UnitSystem.METRIC)
                    .retrieve()
                    .asJava();
            Forecast forecast = openWeatherClient
                    .forecast5Day3HourStep()
                    .byCityName(city)
                    .language(Language.RUSSIAN)
                    .unitSystem(UnitSystem.METRIC)
                    .count(15)
                    .retrieve()
                    .asJava();

            return mapper.map(weather, forecast);

        } catch (NoDataFoundException e) {
            if (e.getMessage() != null && e.getMessage().contains("Please, check requested location.")) throw new CityNotFoundException();
            throw e;
        }
    }
}
