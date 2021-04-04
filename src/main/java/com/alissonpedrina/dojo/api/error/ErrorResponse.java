package com.alissonpedrina.dojo.api.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ErrorResponse {

    private String message;
    private int code;
    private String info;

}