/**
 *Web
 */
package com.testting.exercise.service.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.testting.exercise.service.ConversorService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ConversorController {

    /**
     * Conversor service to inject.
     */
    private final ConversorService iconversorService;

    /**
     * default constructor.
     * @param conversorService
     */
    public ConversorController(final ConversorService conversorService) {
        this.iconversorService = conversorService;
    }

    /**
     * EUR-USD Conversor using the best Rate.
     * @param amount
     * @return String
     */
    @GetMapping("/convert/eur/usd/{amount}")
    public String convert(@PathVariable final String amount) {
        return iconversorService.findBestRateEURForUSD(amount);
    }
}
