package com.learnjava.apiclient;

import com.learnjava.domain.movie.Movie;
import com.learnjava.domain.movie.MovieInfo;
import com.learnjava.domain.movie.Review;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class MoviesClient {

    private final WebClient webClient;

    public MoviesClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Movie retrieveMovie(String movieInfoId) {
        // Need MovieInfo
        var movieInfo = invokeMovieInfoService(movieInfoId);
        // Need Review Data
        var reviews = invokeReviewsService(movieInfoId);
        return new Movie(movieInfo, reviews);
    }

    public CompletableFuture<Movie> retrieveMovieCF(String movieInfoId) {
        // Need MovieInfo
        var movieInfoCF = CompletableFuture.supplyAsync(() -> invokeMovieInfoService(movieInfoId));
        // Need Review Data
        var reviewsCF = CompletableFuture.supplyAsync(() -> invokeReviewsService(movieInfoId));
        return movieInfoCF.thenCombine(reviewsCF, Movie::new);
    }

    private MovieInfo invokeMovieInfoService(String movieInfoId) {
        var moviesInfoUrlPath = "/v1/movie_infos/{movieInfoId}";

        return webClient
                .get()
                .uri(moviesInfoUrlPath, movieInfoId)
                .retrieve()
                .bodyToMono(MovieInfo.class)// used for single objects
                .block();
    }

    private List<Review> invokeReviewsService(String movieInfoId) {
        var reviewUri = UriComponentsBuilder.fromUriString("/v1/reviews")
                .queryParam("movieInfoId", movieInfoId)
                .buildAndExpand()
                .toString();

        return webClient
                .get()
                .uri(reviewUri)
                .retrieve()
                .bodyToFlux(Review.class)// used for collections
                .collectList()
                .block();
    }

    public List<Movie> retrieveMovies(List<String> movieInfoIds) {
        return movieInfoIds.stream()
                .map(this::retrieveMovie)
                .collect(Collectors.toList());
    }

    public List<Movie> retrieveMoviesCF(List<String> movieInfoIds) {
        var movieFutures = movieInfoIds.stream()
                .map(this::retrieveMovieCF)
                .collect(Collectors.toList());

        return movieFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public List<Movie> retrieveMoviesCFAllOf(List<String> movieInfoIds) {
        var movieFutures = movieInfoIds.stream()
                .map(this::retrieveMovieCF)
                .collect(Collectors.toList());

        var movieFuturesArray = movieFutures.toArray(new CompletableFuture[movieInfoIds.size()]);
        var cfAllOf = CompletableFuture.allOf(movieFuturesArray); // will only return if all CF tasks finish
        return cfAllOf.thenApply(ignore -> movieFutures
                        .stream()
                        .map(CompletableFuture::join) // this is not wait due to line 83. This is just to get the value of the future immediately
                        .collect(Collectors.toList()))
                .join();
    }
}
