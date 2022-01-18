package com.alissonpedrina.java17.api.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "lon",
        "lat"
})
public class Coord {

    @JsonProperty("lon")
    private Double lon;
    @JsonProperty("lat")
    private Double lat;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
