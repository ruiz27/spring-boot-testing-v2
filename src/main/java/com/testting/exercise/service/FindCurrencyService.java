package com.testting.exercise.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FindCurrencyService {

    /**
     * Rest.
     */
    private final RestTemplate restTemplate;

    /**
     * FindRateService.
     * @param restTemplateBuilder - restTemplateBuilder
     */
    public FindCurrencyService(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * findRate.
     * @param url  - url
     * @param currency
     * @return CompletableFuture<String>
     */
    @Async
    public CompletableFuture<String> findRate(final String url,
            final String currency) {
        String results = restTemplate.getForObject(url, String.class);
        Double val = JsonPath.parse(results)
                .read("$.rates['" + currency + "']");
        return CompletableFuture.completedFuture(val.toString());
    }

}
