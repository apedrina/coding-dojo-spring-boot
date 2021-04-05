package com.alissonpedrina.dojo.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.InitializingBean;

public interface WeatherStrategy<T> extends InitializingBean {

    T process(String... args) throws JsonProcessingException;

}
