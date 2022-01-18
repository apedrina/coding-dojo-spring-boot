package com.alissonpedrina.java17.api.model;

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
        "all"
})
public class Clouds {

    @JsonProperty("all")
    private Integer all;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
