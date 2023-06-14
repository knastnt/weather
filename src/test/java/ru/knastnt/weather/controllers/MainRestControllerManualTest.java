package ru.knastnt.weather.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.knastnt.weather.WeatherApplicationTests;

class MainRestControllerManualTest extends WeatherApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Test
    @Disabled
    @SneakyThrows
    void successRequest() {
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
                        "   \"description\":\"Document will send on 456kot@mail.ru\"" +
                        "}"
                ));

        Thread.sleep(300000);
    }
}