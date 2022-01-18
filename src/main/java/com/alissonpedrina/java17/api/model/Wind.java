package com.alissonpedrina.java17.api.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "speed",
        "deg",
        "gust"
})
public class Wind {

    @JsonProperty("speed")
    private Double speed;
    @JsonProperty("deg")
    private Integer deg;
    @JsonProperty("gust")
    private String gust;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
