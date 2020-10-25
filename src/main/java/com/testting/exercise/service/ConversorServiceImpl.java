/**
 * Implentaciones
 */
package com.testting.exercise.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConversorServiceImpl implements ConversorService {

    private final FindCurrencyService findCurrencyService;

    @Value("${api.frankfurter}")
    private String frankfurter;
    @Value("${api.ratesapi}")
    private String ratesapi;
    @Value("${api.exchangeratesapi}")
    private String exchangeratesapi;
    @Value("${currency.origin}")
    private String USD;

    public ConversorServiceImpl(final FindCurrencyService finalRateService) {
        this.findCurrencyService = finalRateService;
    }

    /**
     * Find the best rate for currency and return the product
     * 
     * @param amount
     * @return String
     */
    public String findBestRateEURForUSD(final String amount) {

        CompletableFuture<String> future1 = createCallFuture(frankfurter, USD);
        CompletableFuture<String> future2 = createCallFuture(ratesapi, USD);
        CompletableFuture<String> future3 = createCallFuture(exchangeratesapi, USD);

        String resultPararell = Stream.of(future1, future2, future3).map(CompletableFuture::join)
                .collect(Collectors.joining(","));

        Optional<String> op = Stream.of(resultPararell.split(",")).collect(Collectors.maxBy(String::compareTo));

        if (op.isPresent()) {
            return String.valueOf(Double.valueOf(amount) * Double.valueOf(op.get()));
        }

        return amount;

    }

    /**
     * Create call for async call to currency service.
     * 
     * @param url
     * @param currency
     * @return
     */
    private CompletableFuture<String> createCallFuture(String url, String currency) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                return findCurrencyService.findRate(url, currency).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return "0.0";
        });
    }

}
