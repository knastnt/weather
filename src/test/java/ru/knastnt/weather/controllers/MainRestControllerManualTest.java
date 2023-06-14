package ru.knastnt.weather.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.knastnt.weather.WeatherApplicationTests;
import ru.knastnt.weather.controllers.dtos.GetWeatherRequestDto;
import ru.knastnt.weather.logic.WeatherSupplyService;
import ru.knastnt.weather.transport.TransportService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

class MainRestControllerManualTest extends WeatherApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WeatherSupplyService weatherSupplyService;
    @Autowired
    private TransportService transportService;
    @Test
    @Disabled
    @SneakyThrows
    void successRequest() {
        /*Mockito.doAnswer(invocationOnMock -> {
            byte[] content = invocationOnMock.getArgument(0);
            Files.write(Path.of("e:/example.pdf"), content);
            System.out.println("DONE file creation");
            return null;
        }).when(transportService).sendContent(any(), any());*/

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/send-pdf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{" +
                                        "   \"city\": \"Комсомольск-на-Амуре\"," +
                                        "   \"email\": \"456kot@mail.ru\"" +
                                        "}"
                        )
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
            "{" +
                        "   \"status\":\"SUCCESS\"," +
                        "   \"description\":\"Document sent on 456kot@mail.ru\"" +
                        "}"
                ));

        Thread.sleep(300000);
    }
}