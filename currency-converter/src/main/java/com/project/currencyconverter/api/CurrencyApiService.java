package com.project.currencyconverter.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyApiService {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public CurrencyApiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public double getExchangeRate(String fromCurrency, String toCurrency) throws Exception {
        String url = API_URL + fromCurrency.toUpperCase();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new RuntimeException("API request failed with status: " + response.statusCode());
        }

        JsonNode rootNode = objectMapper.readTree(response.body());
        return rootNode.path("rates").path(toCurrency.toUpperCase()).asDouble();
    }
}