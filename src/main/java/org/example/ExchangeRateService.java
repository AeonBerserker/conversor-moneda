package org.example;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ExchangeRateService {

    private static final String API_KEY = "d090f563b260321c90c353e6";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    private final HttpClient httpClient;
    private final Gson gson;

    public ExchangeRateService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public double convertir(String from, String to, double amount) {
        double rate = obtenerTasa(from, to);
        return amount * rate;
    }

    public double obtenerTasa(String from, String to) {
        try {
            CurrencyRateResponse response = obtenerTasas(from);
            Map<String, Double> rates = response.getConversion_rates();
            if (!rates.containsKey(to)) {
                throw new RuntimeException("No se encontró la tasa para " + to);
            }
            return rates.get(to);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al consultar la API de tasas de cambio", e);
        }
    }

    private CurrencyRateResponse obtenerTasas(String baseCurrency)
            throws IOException, InterruptedException {

        String url = BASE_URL + API_KEY + "/latest/" + baseCurrency;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Respuesta no exitosa de la API: " + response.statusCode());
        }

        return gson.fromJson(response.body(), CurrencyRateResponse.class);
    }
}
