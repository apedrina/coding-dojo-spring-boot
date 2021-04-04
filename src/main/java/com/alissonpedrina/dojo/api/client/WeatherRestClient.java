package com.alissonpedrina.dojo.api.client;

import com.alissonpedrina.dojo.api.error.DojoErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@Slf4j
@Component
public class WeatherRestClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.weather.api.url}")
    private String url;

    @Value("${app.weather.api.key}")
    private String apiKey;

    private HttpHeaders headers;

    public WeatherRestClient() {
        headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

    }

    public HttpEntity<String> exchange(String path, Map<String, String> params) {
        var builder = buildParams(path, params);
        var expanded = new UriTemplate(builder.toUriString());
        String urlEncoded = null;
        try {
            urlEncoded = URLDecoder.decode(expanded.toString(), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            log.error("Url params invalid.");
            throw new DojoErrorException(e.getMessage(), e);

        }
        return restTemplate.exchange(
                urlEncoded,
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                String.class);

    }

    private UriComponentsBuilder buildParams(String path, Map<String, String> params) {
        log.info("building WeatherRestClient params with key: {}", apiKey);
        var finalUrl = String.format("%s/%s", url, path);
        log.info("url: {}", finalUrl);
        var builder = UriComponentsBuilder.fromHttpUrl(
                finalUrl)
                .queryParam(WeatherClientEnum.PARAM_KEY.toString(), apiKey);

        params.entrySet().stream().forEach(param -> {
            log.info("adding param: {},{}", param.getKey(), param.getValue());
            builder.queryParam(param.getKey(), param.getValue());

        });
        return builder;

    }

}
