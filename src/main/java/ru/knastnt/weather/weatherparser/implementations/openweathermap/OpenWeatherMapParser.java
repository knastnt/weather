package ru.knastnt.weather.weatherparser.implementations.openweathermap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.knastnt.weather.weatherparser.CityNotFoundException;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;
import ru.knastnt.weather.weatherparser.implementations.WeatherParser;
import ru.knastnt.weather.weatherparser.implementations.openweathermap.dtos.OpenWeatherMapResponseDto;

@Slf4j
@Component
public class OpenWeatherMapParser implements WeatherParser {
    @Autowired
    private OpenWeatherMapMapper mapper;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Override
    public WeatherDto parseForCity(String city) {
        String url = new StringBuilder()
                .append("https://api.openweathermap.org/data/2.5/weather?q=")
                .append(city)
                .append("&lang=RU")
                .append("&units=metric")
                .append("&appid=")
                .append(apiKey)
                .toString();

        try {
            ResponseEntity<OpenWeatherMapResponseDto> result = new RestTemplate()
                    .exchange(url, HttpMethod.GET, null, OpenWeatherMapResponseDto.class);
            log.debug("Result of calling REST: status: {}, result body: {}", result.getStatusCode(), result.getBody());
            return mapper.map(result.getBody());
        } catch (Exception e) {
            if (e.getMessage().contains("city not found")){
                throw new CityNotFoundException();
            }
            log.error("Exception in REST calling", e);
            throw e;
        }


        /*URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        InputStream response = connection.getInputStream();

        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next(); // считываем ответ сервера
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONObject main = jsonObject.getJSONObject("main");
            double temperature = main.getDouble("temp"); // температура в Кельвинах
            int pressure = main.getInt("pressure"); // атмосферное давление
            int humidity = main.getInt("humidity"); // влажность
            JSONObject wind = jsonObject.getJSONObject("wind");
            double windSpeed = wind.getDouble("speed"); // скорость ветра
            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            JSONObject weather = weatherArray.getJSONObject(0);
            String description = weather.getString("description"); // описание погоды
            System.out.println("Weather in " + city + ": " + description + ", Temperature: " + (temperature - 273.15) + " °C, Pressure: " + pressure + " hPa, Humidity: " + humidity + " %, Wind speed: " + windSpeed + " m/s");
        }*/
    }
}
