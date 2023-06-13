package ru.knastnt.weather;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import ru.knastnt.weather.logic.WeatherSupplyService;
import ru.knastnt.weather.weatherparser.implementations.WeatherParser;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@SpyBean(WeatherSupplyService.class)
@SpyBean(WeatherParser.class)
public abstract class WeatherApplicationTests {}
