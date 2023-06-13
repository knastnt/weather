package ru.knastnt.weather;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.knastnt.weather.logic.WeatherSupplyService;

@SpringBootTest
@AutoConfigureMockMvc
@SpyBean(WeatherSupplyService.class)
public abstract class WeatherApplicationTests {}
