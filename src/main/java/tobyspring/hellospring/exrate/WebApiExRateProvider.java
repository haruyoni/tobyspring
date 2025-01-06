package tobyspring.hellospring.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tobyspring.hellospring.api.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;


public class WebApiExRateProvider  implements ExRateProvider {
    ApiTemplate apiTemplate = new ApiTemplate();

    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return apiTemplate.getExRate(url, uri -> {
            HttpRequest request =  HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            try (HttpClient client = HttpClient.newBuilder().build()) {
                client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return null;
        }, new ErApiExRateExtractor());
    }
}
