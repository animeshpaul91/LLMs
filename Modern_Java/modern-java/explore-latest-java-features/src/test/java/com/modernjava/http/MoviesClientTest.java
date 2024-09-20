package com.modernjava.http;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@Disabled
public class MoviesClientTest {
    private final MoviesClient moviesClient = new MoviesClient();
    @Test
    void getMovieById() {
        final Movie movie = moviesClient.getMovieById();
        assertNotNull(movie);
        assertEquals("Batman Begins", movie.name());
    }
    @Test
    void getMovieByIdAsync() {
        final Movie movie = moviesClient.getMovieByIdAsync().toCompletableFuture().join();
        assertNotNull(movie);
        assertEquals("Batman Begins", movie.name());
    }

    @Test
    void getAllMovies() {
        final List<Movie> movies = moviesClient.getAllMovies();
        assertNotNull(movies);
        assertEquals(10, movies.size());
    }
}
