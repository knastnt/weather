package ru.knastnt.weather.doc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

@Slf4j
@Service
public class PdfDocumentCreationService implements DocumentCreationService {
    @Override
    public byte[] createWeatherDocument(WeatherDto weather) {
        log.debug("Create weather document for: {}", weather);

        return new byte[0];
    }
}
