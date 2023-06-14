package ru.knastnt.weather.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

class MainRestControllerTest extends WeatherApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WeatherSupplyService weatherSupplyService;

    @Nested
    class RequestValidation {
        @BeforeEach
        void init() {
            Mockito.doNothing().when(weatherSupplyService).sendWeatherDetails(any());
        }
        @Test
        @SneakyThrows
        void nullFields_badRequest() {
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/send-pdf")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                    "{}"
                            )
                    )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(
                            "{" +
                                    "   \"status\":\"ERROR\"," +
                                    "   \"description\":\"Request validation errors: city: must not be empty; email: must not be empty\"" +
                                    "}"
                    ));
        }

        @Test
        @SneakyThrows
        void emptyFields_badRequest() {
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/send-pdf")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                    "{" +
                                            "   \"city\": \"\"," +
                                            "   \"email\": \"\"" +
                                            "}"
                            )
                    )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(
                            "{" +
                                    "   \"status\":\"ERROR\"," +
                                    "   \"description\":\"Request validation errors: city: must not be empty; email: must not be empty\"" +
                                    "}"
                    ));
        }

        @Test
        @SneakyThrows
        void incorrectEmail_badRequest() {
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/send-pdf")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                    "{" +
                                            "   \"city\": \"Комсомольск-на-Амуре\"," +
                                            "   \"email\": \"456kotmail.ru\"" +
                                            "}"
                            )
                    )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(
                "{" +
                            "   \"status\":\"ERROR\"," +
                            "   \"description\":\"Request validation errors: email: must be a well-formed email address\"" +
                            "}"
                    ));
        }

        @Test
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

            ArgumentCaptor<GetWeatherRequestDto> ac = ArgumentCaptor.forClass(GetWeatherRequestDto.class);
            Mockito.verify(weatherSupplyService).sendWeatherDetails(ac.capture());

            assertThat(ac.getValue().getCity()).isEqualTo("Комсомольск-на-Амуре");
            assertThat(ac.getValue().getEmail()).isEqualTo("456kot@mail.ru");
        }
    }
}