package com.alissonpedrina.dojo.api.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "temp",
        "pressure",
        "humidity",
        "temp_min",
        "temp_max",
        "feels_like"
})
public class Main {

    @JsonProperty("temp")
    private Double temp;
    @JsonProperty("pressure")
    private Integer pressure;
    @JsonProperty("humidity")
    private Integer humidity;
    @JsonProperty("temp_min")
    private Double tempMin;
    @JsonProperty("temp_max")
    private Double tempMax;
    @JsonProperty("feels_like")
    private Double feelsLike;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
