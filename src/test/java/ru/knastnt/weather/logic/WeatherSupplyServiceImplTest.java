package ru.knastnt.weather.logic;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import ru.knastnt.weather.WeatherApplicationTests;
import ru.knastnt.weather.controllers.dtos.GetWeatherRequestDto;
import ru.knastnt.weather.doc.DocumentCreationService;
import ru.knastnt.weather.transport.TransportService;
import ru.knastnt.weather.weatherparser.WeatherParserService;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

class WeatherSupplyServiceImplTest extends WeatherApplicationTests {
    @Autowired
    private WeatherSupplyService weatherSupplyService;
    @Autowired
    private WeatherParserService weatherParser;
    @Autowired
    private DocumentCreationService documentCreationService;
    @Autowired
    private TransportService transportService;

    @Test
    @SneakyThrows
    void normal() {
        GetWeatherRequestDto getWeatherRequestDto = new GetWeatherRequestDto() {{ setCity("комсомольск-на-амуре"); setEmail("mail@ma.il"); }};
        WeatherDto weatherDto = new WeatherDto();
        byte[] content = new byte[3];

        Mockito.doReturn(weatherDto).when(weatherParser).parseForCity(any());
        Mockito.doReturn(content).when(documentCreationService).createWeatherDocument(any());
        Mockito.doNothing().when(transportService).sendContent(any(), any());

        weatherSupplyService.sendWeatherDetails(getWeatherRequestDto);

        Mockito.verify(weatherParser).parseForCity("комсомольск-на-амуре");
        Mockito.verify(documentCreationService, Mockito.timeout(500L)).createWeatherDocument(weatherDto);
        Mockito.verify(transportService, Mockito.timeout(500L)).sendContent(content, "mail@ma.il");
    }

    @Test
    @SneakyThrows
    void exParsing() {
        GetWeatherRequestDto getWeatherRequestDto = new GetWeatherRequestDto() {{ setCity("комсомольск-на-амуре"); setEmail("mail@ma.il"); }};
        WeatherDto weatherDto = new WeatherDto();
        byte[] content = new byte[3];

        Mockito.doThrow(new RuntimeException("synthetic")).when(weatherParser).parseForCity(any());
        Mockito.doReturn(content).when(documentCreationService).createWeatherDocument(any());
        Mockito.doNothing().when(transportService).sendContent(any(), any());

        assertThatThrownBy(() -> weatherSupplyService.sendWeatherDetails(getWeatherRequestDto)).hasMessage("Can't parse weather for city: \"комсомольск-на-амуре\"");

        Mockito.verify(weatherParser, Mockito.times(3)).parseForCity("комсомольск-на-амуре");
        Mockito.verify(documentCreationService, Mockito.timeout(500L).times(0)).createWeatherDocument(weatherDto);
        Mockito.verify(transportService, Mockito.timeout(500L).times(0)).sendContent(content, "mail@ma.il");
    }

    @Test
    @SneakyThrows
    void exDocCreating() {
        GetWeatherRequestDto getWeatherRequestDto = new GetWeatherRequestDto() {{ setCity("комсомольск-на-амуре"); setEmail("mail@ma.il"); }};
        WeatherDto weatherDto = new WeatherDto();
        byte[] content = new byte[3];

        Mockito.doReturn(weatherDto).when(weatherParser).parseForCity(any());
        Mockito.doThrow(new RuntimeException("synthetic")).when(documentCreationService).createWeatherDocument(any());
        Mockito.doNothing().when(transportService).sendContent(any(), any());

        weatherSupplyService.sendWeatherDetails(getWeatherRequestDto);

        Mockito.verify(weatherParser).parseForCity("комсомольск-на-амуре");
        Mockito.verify(documentCreationService, Mockito.timeout(500L)).createWeatherDocument(weatherDto);
        Mockito.verify(transportService, Mockito.timeout(500L).times(0)).sendContent(content, "mail@ma.il");
    }

    @Test
    @SneakyThrows
    void exEmailSending() {
        GetWeatherRequestDto getWeatherRequestDto = new GetWeatherRequestDto() {{ setCity("комсомольск-на-амуре"); setEmail("mail@ma.il"); }};
        WeatherDto weatherDto = new WeatherDto();
        byte[] content = new byte[3];

        Mockito.doReturn(weatherDto).when(weatherParser).parseForCity(any());
        Mockito.doReturn(content).when(documentCreationService).createWeatherDocument(any());
        Mockito.doThrow(new RuntimeException("synthetic")).when(transportService).sendContent(any(), any());

        weatherSupplyService.sendWeatherDetails(getWeatherRequestDto);

        Mockito.verify(weatherParser).parseForCity("комсомольск-на-амуре");
        Mockito.verify(documentCreationService, Mockito.timeout(500L)).createWeatherDocument(weatherDto);
        Mockito.verify(transportService, Mockito.timeout(5000L).times(3)).sendContent(content, "mail@ma.il");
    }
}