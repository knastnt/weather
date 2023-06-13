package ru.knastnt.weather.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.knastnt.weather.controllers.dtos.GetWeatherRequestDto;
import ru.knastnt.weather.doc.DocumentCreationService;
import ru.knastnt.weather.transport.TransportService;
import ru.knastnt.weather.weatherparser.WeatherParserService;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

@Slf4j
@Service
public class WeatherSupplyServiceImpl implements WeatherSupplyService {
    @Autowired
    private WeatherParserService weatherParser;
    @Autowired
    private DocumentCreationService documentCreationService;
    @Autowired
    private TransportService transportService;

    @Override
    public void sendWeatherDetails(GetWeatherRequestDto request) {
        log.debug("Send weather details for {}", request);

        WeatherDto weatherDto = weatherParser.parseForCity(request.getCity());

        byte[] weatherDocument = documentCreationService.createWeatherDocument(weatherDto);

        transportService.sendContent(weatherDocument, request.getEmail());
    }
}
