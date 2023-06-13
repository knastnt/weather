package ru.knastnt.weather.weatherparser.implementations.openweathermap;

import com.github.prominence.openweathermap.api.model.Coordinate;
import com.github.prominence.openweathermap.api.model.Temperature;
import com.github.prominence.openweathermap.api.model.WeatherState;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import com.github.prominence.openweathermap.api.model.weather.Location;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import com.github.prominence.openweathermap.api.model.weather.Wind;
import org.junit.jupiter.api.Test;
import ru.knastnt.weather.weatherparser.dtos.TimeWeatherDto;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.knastnt.weather.weatherparser.dtos.WindDirection.EAST;
import static ru.knastnt.weather.weatherparser.dtos.WindDirection.NORTH_WEST;

class OpenWeatherMapMapperTest {
    OpenWeatherMapMapper mapper = new OpenWeatherMapMapper();
    @Test
    void map() {
        Weather weather = new Weather();

        Location location = Location.withValues(1, "ct");
        location.setCountryCode("cc");
        Coordinate coordinate = Coordinate.of(12.345, 67.890);
        location.setCoordinate(coordinate);
        location.setZoneOffset(ZoneOffset.UTC);
        weather.setLocation(location);

        WeatherState weatherState = new WeatherState(1, "cld", "cloudy");
        weatherState.setIconId("icn");
        weather.setWeatherState(weatherState);

        Temperature temperature = Temperature.withValue(12.7, "grd");
        weather.setTemperature(temperature);

        Wind wind = Wind.withValue(1.5, "meter/sec");
        wind.setDegrees(76.7);
        wind.setGust(2.3);
        weather.setWind(wind);

        Forecast forecast = new Forecast();
        forecast.setWeatherForecasts(new ArrayList<>());

        WeatherForecast weatherForecast = new WeatherForecast();
        weatherForecast.setForecastTime(LocalDateTime.of(2023,6,14,0,29,30));
        WeatherState weatherState1 = new WeatherState(1, "cl2", "wsdsc2");
        weatherState1.setIconId("swic2");
        weatherForecast.setWeatherState(weatherState1);

        Temperature temperature1 = Temperature.withValue(22.1, "g");
        weatherForecast.setTemperature(temperature1);

        com.github.prominence.openweathermap.api.model.forecast.Wind wind1 = com.github.prominence.openweathermap.api.model.forecast.Wind.withValue(0.7, "u2");
        wind1.setDegrees(330);
        weatherForecast.setWind(wind1);

        forecast.getWeatherForecasts().add(weatherForecast);


        WeatherDto res = mapper.map(weather, forecast);


        assertThat(res.getCity()).isEqualTo("ct");
        assertThat(res.getCountry()).isEqualTo("cc");
        assertThat(res.getWeather()).isEqualTo("cloudy");
        assertThat(res.getIconName()).isEqualTo("icn");
        assertThat(res.getTemperature()).isEqualTo(13);
        assertThat(res.getTemperatureUnit()).isEqualTo("grd");
        assertThat(res.getCoordinates()).isEqualTo("12° 20′ 42″, 67° 53′ 24″");
        assertThat(res.getZoneOffset()).isEqualTo("");
        assertThat(res.getWindDirection()).isEqualTo(EAST);
        assertThat(res.getWindSpeed()).isEqualTo(2);
        assertThat(res.getWindGust()).isEqualTo(2);
        assertThat(res.getWindUnit()).isEqualTo("м/с");

        assertThat(res.getTimeWeather()).hasSize(1);

        TimeWeatherDto twd = res.getTimeWeather().get(0);
        assertThat(twd.getTime()).isEqualTo(LocalDateTime.of(2023,6,14,0,29,30));
        assertThat(twd.getWeather()).isEqualTo("wsdsc2");
        assertThat(twd.getIconName()).isEqualTo("swic2");
        assertThat(twd.getTemperature()).isEqualTo(22);
        assertThat(twd.getWindDirection()).isEqualTo(NORTH_WEST);
        assertThat(twd.getWindSpeed()).isEqualTo(1);
    }
}