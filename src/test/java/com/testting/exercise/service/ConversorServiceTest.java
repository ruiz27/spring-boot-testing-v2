package com.testting.exercise.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.tomakehurst.wiremock.WireMockServer;

/**
 * Conversor service test.
 * @author sergioruizcombariza
 *
 */
@SpringBootTest
public class ConversorServiceTest {

    /**
     * Wiremock server.
     */
    private WireMockServer wireMockServer;

    /**
     * Object converser service.
     */
    @Autowired
    private ConversorService service;

    /**
     * Initialize wiremock server.
     */
    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(8989);

        wireMockServer.start();

        wireMockServer.stubFor(get(urlEqualTo("/api.exchangeratesapi.io/latest"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain").withStatus(200).withBody(
                        "{\"amount\":1.0,\"base\":\"EUR\",\"date\":\"2020-10-20\",\"rates\":{\"AUD\":1.6801,\"BGN\":1.9558,"
                                + "\"BRL\":6.6182,\"CAD\":1.5577,\"CHF\":1.0724,\"CNY\":7.8918,\"CZK\":27.233,\"DKK\":7.4425,\"GBP\":0.91329,"
                                + "\"HKD\":9.1528,\"HRK\":7.5826,\"HUF\":365.68,\"IDR\":17373,\"ILS\":3.9911,\"INR\":86.85,\"ISK\":164.0,\"JPY\":124.79,"
                                + "\"KRW\":1346.35,\"MXN\":25.032,\"MYR\":4.9017,\"NOK\":10.9698,\"NZD\":1.8006,\"PHP\":57.333,\"PLN\":4.5809,\"RON\":4.8761,"
                                + "\"RUB\":92.02,\"SEK\":10.3805,\"SGD\":1.6036,\"THB\":36.965,\"TRY\":9.3301,\"USD\":1.181,\"ZAR\":19.5245}}")));
    }

    /**
     * Test exchange api find max rate.
     */
    @Test
    public void given_exchangeApiURL_findMax_rate() {
        assertEquals("1.1882", service.findBestRateEURForUSD("1"));
    }

}
