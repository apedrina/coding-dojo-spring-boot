package com.alissonpedrina.it;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = {DatasourceITConfig.class, ApplicationIT.class}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class RestControllerTest extends RestBaseIT {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @ValueSource(strings = {"London,uk", "Noordwijk,nl", "São Paulo,br"})
    public void should_call_weather_endpoint_without_error_using_country_code(String cityName) throws Exception {
        this.mockMvc.perform(get(String.format("/weather/%s", cityName)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

    @ParameterizedTest
    @ValueSource(strings = {"12,32,15,37,10"})
    public void should_call_weather_by_bbox_without_error(String cityName) throws Exception {
        this.mockMvc.perform(get(String.format("/weather/bbox/%s", cityName)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

    @ParameterizedTest
    @ValueSource(strings = {"35,139"})
    public void should_call_weather_by_lat_long_endpoint_without_error(String latLong) throws Exception {
        var coord = latLong.split(",");
        this.mockMvc.perform(get(String.format("/weather/lat/%s/long/%s", coord[0], coord[1])))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

    @ParameterizedTest
    @ValueSource(strings = {"94040,us", "2204,nl"})
    public void should_call_weather_by_zip_code_endpoint_without_error(String id) throws Exception {
        this.mockMvc.perform(get(String.format("/weather/zip/%s", id)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

    @ParameterizedTest
    @ValueSource(strings = {"2172797", "2172797"})
    public void should_call_weather_by_id_endpoint_without_error(String id) throws Exception {
        this.mockMvc.perform(get(String.format("/weather/id/%s", id)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

    @ParameterizedTest
    @ValueSource(strings = {"London", "Noordwijk", "São Paulo"})
    public void should_call_weather_endpoint_without_error(String cityName) throws Exception {
        this.mockMvc.perform(get(String.format("/weather/%s", cityName)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

    @ParameterizedTest
    @ValueSource(strings = {"London", "Noordwijk", "São Paulo"})
    public void should_get_weather_queries(String cityName) throws Exception {
        this.mockMvc.perform(get(String.format("/weather/%s", cityName)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

    @ParameterizedTest
    @ValueSource(strings = {"Londonxx", "Noordwijkxx", "Sao Pauloxx"})
    public void should_get_error_on_try_query_weather(String cityName) throws Exception {
        this.mockMvc.perform(get(String.format("/weather/%s", cityName)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());

    }

    @ParameterizedTest
    @ValueSource(strings = {"/weather/queries"})
    public void get_all_queries(String url) throws Exception {
        this.mockMvc.perform(get(url))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

}

