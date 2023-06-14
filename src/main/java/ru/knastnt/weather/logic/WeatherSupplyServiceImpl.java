package ru.knastnt.weather.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import ru.knastnt.weather.controllers.dtos.GetWeatherRequestDto;
import ru.knastnt.weather.doc.DocumentCreationService;
import ru.knastnt.weather.transport.TransportService;
import ru.knastnt.weather.weatherparser.WeatherParserService;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class WeatherSupplyServiceImpl implements WeatherSupplyService {
    @Autowired
    private WeatherParserService weatherParser;
    @Autowired
    private DocumentCreationService documentCreationService;
    @Autowired
    private TransportService transportService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void sendWeatherDetails(GetWeatherRequestDto request) {
        log.debug("Send weather details for {}", request);

        WeatherDto weatherDto = weatherParser.parseForCity(request.getCity());

        ListenableFuture<byte[]> fileContentFuture = taskExecutor.submitListenable(
                () -> documentCreationService.createWeatherDocument(weatherDto));

        fileContentFuture.addCallback(
                successResult -> {
                    taskExecutor.submit(() -> transportService.sendContent(successResult, request.getEmail(), weatherDto.getCity()));
                },
                ex -> {
                    log.error("FileContentFuture ends with exception", ex);
                });

        log.debug("Weather document creation and sending submitted");
    }
}
