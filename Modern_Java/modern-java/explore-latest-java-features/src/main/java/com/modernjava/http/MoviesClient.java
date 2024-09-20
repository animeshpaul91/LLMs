package com.modernjava.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class MoviesClient {
    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(2))
            .build();

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    public static String ALL_MOVIES_URL = "http://127.0.0.1:8000/explore-latest-java-features/src/main/resources/movies.json";
    public static String MOVIE_BY_ID_URL = "http://127.0.0.1:8000/explore-latest-java-features/src/main/resources/movie_by_id.json";

    public Movie getMovieById() {
        final var httpRequest = requestBuilder(MOVIE_BY_ID_URL);
        try {
            final var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status Code: " + response.statusCode());
            return objectMapper.readValue(response.body(), Movie.class);
        } catch (final IOException | InterruptedException e) {
            System.err.println(e);
            return null;
        }
    }

    public CompletionStage<Movie> getMovieByIdAsync() {
        final var httpRequest = requestBuilder(MOVIE_BY_ID_URL);
        final var responseFuture = client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

        return responseFuture.thenApply(httpResponse -> {
            System.out.println("Status Code: " + httpResponse.statusCode());
            try {
                return objectMapper.readValue(httpResponse.body(), Movie.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<Movie> getAllMovies() {
        final var httpRequest = requestBuilder(ALL_MOVIES_URL);
        try {
            final var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            final var body = response.body();
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body is: " + body);
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        } catch (final IOException | InterruptedException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }

    private static HttpRequest requestBuilder(final String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }
}
