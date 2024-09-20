package com.learnwiremock.service;

import com.learnwiremock.dto.Movie;
import com.learnwiremock.exceptions.MovieErrorResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class MoviesClientTest {
    private MoviesClient moviesClient;
    @BeforeEach
    void setUp() {
        final String baseUrl = "http://localhost:8081";
        WebClient webClient = WebClient.create(baseUrl);
        moviesClient = new MoviesClient(webClient);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testRetrieveAllMovies() {
        // when
        final var moviesList = moviesClient.retrieveAllMovies();
        System.out.println(moviesList);

        // then
        assertFalse(moviesList.isEmpty());
    }

    @Test
    void testGetMovieByIdValidMovie() {
        // given
        final Integer movieId = 1;

        // when
        final var movie = moviesClient.retrieveMovieById(movieId);
        System.out.println(movie);

        // then
        assertNotNull(movie);
        assertNotNull(movie.getMovie_id());
    }

    @Test
    void testGetMovieByIdInvalidMovie() {
        // given
        final Integer movieId = 100;

        // when and then
        assertThrows(MovieErrorResponse.class, () -> moviesClient.retrieveMovieById(movieId));
    }

    @Test
    void testGetMovieByNameValidMovie() {
        // given
        final String movieName = "Avengers";

        // when
        final var moviesList = moviesClient.retrieveMovieByName(movieName);
        System.out.println(moviesList);

        // then
        assertFalse(moviesList.isEmpty());
        assertEquals(4, moviesList.size());
    }

    @Test
    void testGetMovieByNameInvalidMovie() {
        // given
        final String movieName = "bogus";

        // when and then
        assertThrows(MovieErrorResponse.class, () -> moviesClient.retrieveMovieByName(movieName));
    }

    @Test
    void testGetMovieByYearValidMovie() {
        // given
        final int movieYear = 2012;

        // when
        final var moviesList = moviesClient.retrieveMovieByYear(movieYear);
        System.out.println(moviesList);

        // then
        assertFalse(moviesList.isEmpty());
        assertEquals(2, moviesList.size());
    }

    @Test
    void testGetMovieByYearInvalidMovie() {
        // given
        final int movieYear = 0;

        // when and then
        assertThrows(MovieErrorResponse.class, () -> moviesClient.retrieveMovieByYear(movieYear));
    }

    @Test
    void testCreateMovieValidMovie() {
        // given
        final LocalDate releaseDate = LocalDate.of(1993, Month.DECEMBER, 15);
        final Movie movie = new Movie(null, "Schindler's List", "Liam Neeson, Ben Kingsley", 1993, releaseDate);

        // when
        final var createdMovie = moviesClient.createMovie(movie);
        System.out.println(createdMovie);

        // then
        assertNotNull(createdMovie.getMovie_id());
    }

    @Test
    void testCreateMovieBadMovie() {
        // given
        final LocalDate releaseDate = LocalDate.of(1993, Month.DECEMBER, 15);
        final Movie movie = new Movie(null, null, "Liam Neeson, Ben Kingsley", 1993, releaseDate);

        // when
        final String expectedMessage = "Please pass all input fields";
        assertThrows(MovieErrorResponse.class, () -> moviesClient.createMovie(movie), expectedMessage);
    }

    @Test
    void testUpdateWithValidMovie() {
        // given
        final Integer movieId = 3;
        final String cast = "abc";
        final Movie movie = new Movie();
        movie.setCast(cast);

        // when
        final var updatedMovie = moviesClient.updateMovie(movieId, movie);

        // then
        assertTrue(updatedMovie.getCast().contains(cast));
    }

    @Test
    void testUpdateWithInvalidMovie() {
        // given
        final Integer movieId = 100;
        final String cast = "abc";
        final Movie movie = new Movie();
        movie.setCast(cast);

        // when and then
        assertThrows(MovieErrorResponse.class, () -> moviesClient.updateMovie(movieId, movie));
    }

    @Test
    void testDeleteValidMovie() {
        // given
        // first create movie
        final LocalDate releaseDate = LocalDate.of(1993, Month.DECEMBER, 15);
        final Movie movie = new Movie(null, "New Movie to be deleted", "Liam Neeson, Ben Kingsley", 1993, releaseDate);
        final var createdMovie = moviesClient.createMovie(movie);

        // when
        // then delete
        final String expectedMessage = "Movie Deleted Successfully";
        final String actualMessage = moviesClient.deleteMovie(createdMovie.getMovie_id().intValue());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testDeleteInvalidMovie() {
        // given
        final Integer movieId = 100;

        // when and then
        assertThrows(MovieErrorResponse.class, () -> moviesClient.deleteMovie(movieId));
    }
}
