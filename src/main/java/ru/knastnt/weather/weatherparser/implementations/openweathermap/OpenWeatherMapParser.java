package ru.knastnt.weather.weatherparser.implementations.openweathermap;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
        OpenWeatherMapClient openWeatherClient = new OpenWeatherMapClient(apiKey);

        final Forecast forecast = openWeatherClient
                .forecast5Day3HourStep()
                .byCityName(city)
                .language(Language.RUSSIAN)
                .unitSystem(UnitSystem.METRIC)
                .count(15)
                .retrieve()
                .asJava();

        return mapper.map(forecast);
    }
}
