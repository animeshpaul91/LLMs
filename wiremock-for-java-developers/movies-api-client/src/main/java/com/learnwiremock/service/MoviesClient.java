package com.learnwiremock.service;

import com.learnwiremock.dto.Movie;
import com.learnwiremock.exceptions.MovieErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.learnwiremock.constants.MoviesAppConstants.CREATE_MOVIE;
import static com.learnwiremock.constants.MoviesAppConstants.DELETE_MOVIE;
import static com.learnwiremock.constants.MoviesAppConstants.DELETE_MOVIE_BY_NAME;
import static com.learnwiremock.constants.MoviesAppConstants.GET_ALL_MOVIES_V1;
import static com.learnwiremock.constants.MoviesAppConstants.GET_MOVIE_BY_ID;
import static com.learnwiremock.constants.MoviesAppConstants.GET_MOVIE_BY_NAME;
import static com.learnwiremock.constants.MoviesAppConstants.GET_MOVIE_BY_YEAR;
import static com.learnwiremock.constants.MoviesAppConstants.UPDATE_MOVIE;

@Slf4j
public class MoviesClient {
    private final WebClient webClient;

    public MoviesClient(final WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Movie> retrieveAllMovies() {
        try {
            return webClient.get()
                    .uri(GET_ALL_MOVIES_V1)
                    .retrieve()
                    .bodyToFlux(Movie.class)
                    .collectList()
                    .block();
        } catch (final WebClientResponseException ex) {
            log.error("WebClientResponseException when attempting to retrieve list of movies | Status Code {} | Message {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new MovieErrorResponse(ex.getStatusText(), ex);
        } catch (final Throwable throwable) {
            log.error("Exception when attempting to retrieve list of movies | Message {}", throwable.getMessage(), throwable);
            throw new MovieErrorResponse(throwable);
        }
    }

    public Movie retrieveMovieById(final Integer movieId) {
        try {
            return webClient.get()
                    .uri(GET_MOVIE_BY_ID, movieId)
                    .retrieve() // makes the invocation of the endpoint
                    .bodyToMono(Movie.class)
                    .block();
        } catch (final WebClientResponseException ex) {
            log.error("WebClientResponseException when attempting to retrieve movie by ID {} | Status Code {} | Message {}", movieId, ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new MovieErrorResponse(ex.getStatusText(), ex);
        } catch (final Throwable throwable) {
            log.error("Exception when attempting to retrieve movie by ID {}", movieId, throwable);
            throw new MovieErrorResponse(throwable);
        }
    }

    public List<Movie> retrieveMovieByName(final String movieName) {
        final String retrieveByNameUri = UriComponentsBuilder.fromUriString(GET_MOVIE_BY_NAME)
                .queryParam("movie_name", movieName)
                .buildAndExpand()
                .toUriString();

        try {
            return webClient.get()
                    .uri(retrieveByNameUri)
                    .retrieve()
                    .bodyToFlux(Movie.class)
                    .collectList()
                    .block();
        } catch (final WebClientResponseException ex) {
            log.error("WebClientResponseException when attempting to retrieve movie by name {} | Status Code {} | Message {}", movieName, ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new MovieErrorResponse(ex.getStatusText(), ex);
        } catch (final Throwable throwable) {
            log.error("Exception when attempting to retrieve movie by name {}", movieName, throwable);
            throw new MovieErrorResponse(throwable);
        }
    }

    public List<Movie> retrieveMovieByYear(final int movieYear) {
        final String retrieveByYearUri = UriComponentsBuilder.fromUriString(GET_MOVIE_BY_YEAR)
                .queryParam("year", movieYear)
                .buildAndExpand()
                .toUriString();

        try {
            return webClient.get()
                    .uri(retrieveByYearUri)
                    .retrieve()
                    .bodyToFlux(Movie.class)
                    .collectList()
                    .block();
        } catch (final WebClientResponseException ex) {
            log.error("WebClientResponseException when attempting to retrieve movie by year {} | Status Code {} | Message {}", movieYear, ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new MovieErrorResponse(ex.getStatusText(), ex);
        } catch (final Throwable throwable) {
            log.error("Exception when attempting to retrieve movie by year {}", movieYear, throwable);
            throw new MovieErrorResponse(throwable);
        }
    }

    public Movie createMovie(final Movie movie) {
        try {
            return webClient.post()
                    .uri(CREATE_MOVIE)
                    .syncBody(movie)
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
        } catch (final WebClientResponseException ex) {
            log.error("WebClientResponseException when attempting to create movie | Status Code {} | Message {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new MovieErrorResponse(ex.getStatusText(), ex);
        } catch (final Throwable throwable) {
            log.error("Exception when attempting to create movie", throwable);
            throw new MovieErrorResponse(throwable);
        }
    }

    public Movie updateMovie(final Integer movieId, final Movie movie) {
        try {
            return webClient.put()
                    .uri(UPDATE_MOVIE, movieId)
                    .syncBody(movie)
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
        } catch (final WebClientResponseException ex) {
            log.error("WebClientResponseException when attempting to update movie | Status Code {} | Message {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new MovieErrorResponse(ex.getStatusText(), ex);
        } catch (final Throwable throwable) {
            log.error("Exception when attempting to update movie", throwable);
            throw new MovieErrorResponse(throwable);
        }
    }

    public String deleteMovie(final Integer movieId) {
        try {
            return webClient.delete()
                    .uri(DELETE_MOVIE, movieId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (final WebClientResponseException ex) {
            log.error("WebClientResponseException when attempting to delete movie | Status Code {} | Message {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new MovieErrorResponse(ex.getStatusText(), ex);
        } catch (final Throwable throwable) {
            log.error("Exception when attempting to delete movie", throwable);
            throw new MovieErrorResponse(throwable);
        }
    }

    public String deleteMovieByName(final String movieName) {
        final String deleteMovieByNameUri = UriComponentsBuilder.fromUriString(DELETE_MOVIE_BY_NAME)
                .queryParam("movie_name", movieName)
                .buildAndExpand()
                .toUriString();

        try {
            webClient.delete()
                    .uri(deleteMovieByNameUri)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (final WebClientResponseException ex) {
            log.error("WebClientResponseException when attempting to delete movie | Status Code {} | Message {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new MovieErrorResponse(ex.getStatusText(), ex);
        } catch (final Throwable throwable) {
            log.error("Exception when attempting to delete movie", throwable);
            throw new MovieErrorResponse(throwable);
        }
        return "Movie Deleted Successfully";
    }
}
