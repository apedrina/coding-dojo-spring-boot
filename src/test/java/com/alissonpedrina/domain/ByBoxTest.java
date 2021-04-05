package com.alissonpedrina.domain;

import com.alissonpedrina.commons.TestUtils;
import com.alissonpedrina.dojo.api.model.WeatherResponse;
import com.alissonpedrina.dojo.domain.Query;
import com.alissonpedrina.dojo.domain.query.ByBox;
import com.alissonpedrina.dojo.jms.Producer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class ByBoxTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private Producer producer;
    @Mock
    private Query query;
    @InjectMocks
    private ByBox byBox;

    @Test
    void should_process_by_box() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        var jsonFile = TestUtils.getResourceFileFromJson("classpath:json/weather.json");
        var weatherResponse = mapper.readValue(jsonFile, WeatherResponse.class);
        when(query.callWeatherEndpoint(anyString(), (any()))).thenReturn(jsonFile);
        when(objectMapper.readValue(anyString(), any(Class.class))).thenReturn(weatherResponse);

        byBox.process("12,32,15,37,10");

        verify(producer, times(1)).sendMessage((any(WeatherResponse.class)));

    }

}
