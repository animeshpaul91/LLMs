package com.learnwiremock.service;

import com.github.jenspiegsa.wiremockextension.ConfigureWireMock;
import com.github.jenspiegsa.wiremockextension.InjectServer;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.learnwiremock.dto.Movie;
import com.learnwiremock.exceptions.MovieErrorResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.Month;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.exactly;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.learnwiremock.constants.MoviesAppConstants.CREATE_MOVIE;
import static com.learnwiremock.constants.MoviesAppConstants.DELETE_MOVIE_BY_NAME;
import static com.learnwiremock.constants.MoviesAppConstants.GET_ALL_MOVIES_V1;
import static com.learnwiremock.constants.MoviesAppConstants.GET_MOVIE_BY_NAME;
import static com.learnwiremock.constants.MoviesAppConstants.GET_MOVIE_BY_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(WireMockExtension.class)
class MoviesClientWireMockTest {

    private MoviesClient moviesClient;

    @InjectServer
    private WireMockServer wireMockServer;

    @ConfigureWireMock
    private final Options options = wireMockConfig()
            .port(8088)
            .notifier(new ConsoleNotifier(true))
            .extensions(new ResponseTemplateTransformer(true));

    @BeforeEach
    void setUp() {
        int wiremockPort = wireMockServer.port();
        final String baseUrl = String.format("http://localhost:%s", wiremockPort);
        WebClient webClient = WebClient.create(baseUrl);
        moviesClient = new MoviesClient(webClient);

        // reverse proxy configuration
        int appPort = 8081;
        final String appBaseUrl = String.format("http://localhost:%s", appPort);
        stubFor(any(anyUrl()).willReturn(aResponse().proxiedFrom(appBaseUrl)));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testRetrieveAllMovies() {
        // given
        stubFor(get(anyUrl())
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("all-movies.json")));

        // when
        final var moviesList = moviesClient.retrieveAllMovies();
        System.out.println(moviesList);

        // then
        assertFalse(moviesList.isEmpty());
    }

    @Test
    void testRetrieveAllMoviesMatchesURLPattern() {
        // given
        stubFor(get(urlPathEqualTo(GET_ALL_MOVIES_V1))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("all-movies.json")));

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
        stubFor(get(urlMatching("/movieservice/v1/movie/[0-9]"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("movie.json")));

        // when
        final var movie = moviesClient.retrieveMovieById(movieId);
        System.out.println(movie);

        // then
        assertNotNull(movie);
        assertNotNull(movie.getMovie_id());
    }

    @Test
    void testGetMovieByIdValidMovieResponseTemplating() {
        // given
        stubFor(get(urlMatching("/movieservice/v1/movie/[0-9]"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("movie-template.json")));

        // when
        final Integer movieId = 7;
        final var movie = moviesClient.retrieveMovieById(movieId);
        System.out.println(movie);

        // then
        assertNotNull(movie);
        assertEquals(movieId, movie.getMovie_id().intValue());
    }

    @Test
    void testGetMovieByIdInvalidMovie() {
        // given
        final Integer movieId = 100;
        stubFor(get(urlMatching("/movieservice/v1/movie/[0-9]+"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("movie-404.json")));

        // when and then
        assertThrows(MovieErrorResponse.class, () -> moviesClient.retrieveMovieById(movieId));
    }

    @Test
    void testGetMovieByNameValidMovie() {
        // given
        final String movieName = "Avengers";
        final String urlPattern = GET_MOVIE_BY_NAME + "?movie_name=" + movieName;
        stubFor(get(urlEqualTo(urlPattern))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("avengers.json")));

        // when
        final var moviesList = moviesClient.retrieveMovieByName(movieName);
        System.out.println(moviesList);

        // then
        assertFalse(moviesList.isEmpty());
        assertEquals(4, moviesList.size());
    }

    @Test
    void testGetMovieByNameValidMovieApproach2() {
        // given
        final String movieName = "Avengers";
        stubFor(get(urlPathEqualTo(GET_MOVIE_BY_NAME))
                .withQueryParam("movie_name", equalTo(movieName))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("avengers.json")));

        // when
        final var moviesList = moviesClient.retrieveMovieByName(movieName);
        System.out.println(moviesList);

        // then
        assertFalse(moviesList.isEmpty());
        assertEquals(4, moviesList.size());
    }

    @Test
    void testGetMovieByNameValidMovieResponseTemplating() {
        // given
        final String movieName = "Avengers";
        final String urlPattern = GET_MOVIE_BY_NAME + "?movie_name=" + movieName;
        stubFor(get(urlEqualTo(urlPattern))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("movie-by-name.json")));

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
        final String urlPattern = GET_MOVIE_BY_NAME + "?movie_name=" + movieName;
        stubFor(get(urlEqualTo(urlPattern))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("movie-404.json")));

        // when and then
        assertThrows(MovieErrorResponse.class, () -> moviesClient.retrieveMovieByName(movieName));
    }

    @Test
    void testGetMovieByYearValidMovie() {
        // given
        final int movieYear = 2012;
        final String urlPattern = GET_MOVIE_BY_YEAR + "?year=" + movieYear;
        stubFor(get(urlEqualTo(urlPattern))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("movie-by-year.json")));

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
        final String urlPattern = GET_MOVIE_BY_YEAR + "?year=" + movieYear;
        stubFor(get(urlEqualTo(urlPattern))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("movie-by-year-404.json")));

        // when and then
        assertThrows(MovieErrorResponse.class, () -> moviesClient.retrieveMovieByYear(movieYear));
    }

    @Test
    void testCreateMovieValidMovie() {
        // given
        final String movieName = "Schindler's List";
        final String movieCast = "Liam Neeson, Ben Kingsley";
        stubFor(post(urlEqualTo(CREATE_MOVIE))
                .withRequestBody(matchingJsonPath("$.name", equalTo(movieName)))
                .withRequestBody(matchingJsonPath("$.cast", containing("Ben")))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("create-movie.json")));
        final LocalDate releaseDate = LocalDate.of(1993, Month.DECEMBER, 15);

        // when
        final Movie movie = new Movie(null, movieName, movieCast, 1993, releaseDate);
        final var createdMovie = moviesClient.createMovie(movie);
        System.out.println(createdMovie);

        // then
        assertNotNull(createdMovie.getMovie_id());
    }

    @Test
    void testCreateMovieValidMovieResponseTemplating() {
        // given
        final String movieName = "Schindler's List";
        final String movieCast = "Liam Neeson, Ben Kingsley";
        stubFor(post(urlEqualTo(CREATE_MOVIE))
                .withRequestBody(matchingJsonPath("$.name", equalTo(movieName)))
                .withRequestBody(matchingJsonPath("$.cast", containing("Ben")))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("create-movie-template.json")));
        final LocalDate releaseDate = LocalDate.of(1993, Month.DECEMBER, 15);

        // when
        final Movie movie = new Movie(null, movieName, movieCast, 1993, releaseDate);
        final var createdMovie = moviesClient.createMovie(movie);
        System.out.println(createdMovie);

        // then
        assertNotNull(createdMovie.getMovie_id());
    }

    @Test
    void testCreateMovieBadMovie() {
        // given
        stubFor(post(urlEqualTo(CREATE_MOVIE))
                .withRequestBody(matchingJsonPath("$.cast", containing("Ben")))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("movie-400-invalid.json")));

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

        stubFor(put(urlPathMatching("/movieservice/v1/movie/[0-9]+"))
                .withRequestBody(matchingJsonPath("$.cast", equalTo(cast)))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("update-movie-template.json")));
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

        stubFor(put(urlPathMatching("/movieservice/v1/movie/[0-9]+"))
                .withRequestBody(matchingJsonPath("$.cast", equalTo(cast)))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("movie-404.json")));

        // when and then
        assertThrows(MovieErrorResponse.class, () -> moviesClient.updateMovie(movieId, movie));
    }

    @Test
    void testDeleteValidMovie() {
        // given
        // first create movie
        final var movieName = "New Movie to be deleted";

        // stub for createMovie
        stubFor(post(urlEqualTo(CREATE_MOVIE))
                .withRequestBody(matchingJsonPath("$.name", equalTo(movieName)))
                .withRequestBody(matchingJsonPath("$.cast", containing("Ben")))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("create-movie-template.json")));

        final LocalDate releaseDate = LocalDate.of(1993, Month.DECEMBER, 15);
        final Movie movie = new Movie(null, movieName, "Liam Neeson, Ben Kingsley", 1993, releaseDate);
        final var createdMovie = moviesClient.createMovie(movie);

        // stub for deleteMovie
        final String expectedErrorMessage = "Movie Deleted Successfully";
        stubFor(delete(urlMatching("/movieservice/v1/movie/[0-9]+"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(expectedErrorMessage)));

        // when
        final String actualMessage = moviesClient.deleteMovie(createdMovie.getMovie_id().intValue());
        // then
        assertEquals(expectedErrorMessage, actualMessage);
    }

    @Test
    void testDeleteInvalidMovie() {
        // given
        final Integer movieId = 100;
        final String expectedErrorMessage = "Movie not found";
        stubFor(delete(urlMatching("/movieservice/v1/movie/[0-9]+"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(expectedErrorMessage)));

        // when and then
        assertThrows(MovieErrorResponse.class, () -> moviesClient.deleteMovie(movieId));
    }

    @Test
    void testDeleteValidMovieByName() {
        final String originalMovie = "New Movie to be deleted";
        final String movieName = originalMovie.replaceAll("\\s+", "");

        // stub for createMovie
        stubFor(post(urlEqualTo(CREATE_MOVIE))
                .withRequestBody(matchingJsonPath("$.name", equalTo(movieName)))
                .withRequestBody(matchingJsonPath("$.cast", containing("Ben")))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("create-movie-template.json")));

        final LocalDate releaseDate = LocalDate.of(1993, Month.DECEMBER, 15);
        final Movie movie = new Movie(null, movieName, "Liam Neeson, Ben Kingsley", 1993, releaseDate);
        moviesClient.createMovie(movie);

        // stub for deleteMovie
        final String expectedErrorMessage = "Movie Deleted Successfully";
        stubFor(delete(urlPathEqualTo(DELETE_MOVIE_BY_NAME))
                .withQueryParam("movie_name", equalTo(movieName))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(expectedErrorMessage)));

        // when
        final String actualMessage = moviesClient.deleteMovieByName(movieName);
        // then
        assertEquals(expectedErrorMessage, actualMessage);
        verify(exactly(1), postRequestedFor(urlPathEqualTo(CREATE_MOVIE))
                .withRequestBody(matchingJsonPath("$.name", equalTo(movieName)))
                .withRequestBody(matchingJsonPath("$.cast", containing("Ben"))));
        verify(exactly(1), deleteRequestedFor(urlPathEqualTo(DELETE_MOVIE_BY_NAME))
                .withQueryParam("movie_name", equalTo(movieName)));
    }

    @Test
    void testDeleteInvalidMovieByName() {
        // given
        final String movieName = "bogusMovie";
        final String expectedErrorMessage = "Movie not found";
        stubFor(delete(urlPathEqualTo(DELETE_MOVIE_BY_NAME))
                .withQueryParam("movie_name", equalTo(movieName))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(expectedErrorMessage)));

        // when and then
        assertThrows(MovieErrorResponse.class, () -> moviesClient.deleteMovieByName(movieName));
    }

    @Test
    void testDeleteValidMovieByNameSelectiveProxying() {
        final String originalMovie = "New Movie to be deleted";
        final String movieName = originalMovie.replaceAll("\\s+", "");

        // Remove this stub to redirect request to actual service for selective proxying
        stubFor(post(urlEqualTo(CREATE_MOVIE))
                .withRequestBody(matchingJsonPath("$.name", equalTo(movieName)))
                .withRequestBody(matchingJsonPath("$.cast", containing("Ben")))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("create-movie-template.json")));

        final LocalDate releaseDate = LocalDate.of(1993, Month.DECEMBER, 15);
        final Movie movie = new Movie(null, movieName, "Liam Neeson, Ben Kingsley", 1993, releaseDate);
        moviesClient.createMovie(movie);

        // stub for deleteMovie
        final String expectedErrorMessage = "Movie Deleted Successfully";
        stubFor(delete(urlPathEqualTo(DELETE_MOVIE_BY_NAME))
                .withQueryParam("movie_name", equalTo(movieName))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(expectedErrorMessage)));

        // when
        final String actualMessage = moviesClient.deleteMovieByName(movieName);
        // then
        assertEquals(expectedErrorMessage, actualMessage);
        verify(exactly(1), postRequestedFor(urlPathEqualTo(CREATE_MOVIE))
                .withRequestBody(matchingJsonPath("$.name", equalTo(movieName)))
                .withRequestBody(matchingJsonPath("$.cast", containing("Ben"))));
        verify(exactly(1), deleteRequestedFor(urlPathEqualTo(DELETE_MOVIE_BY_NAME))
                .withQueryParam("movie_name", equalTo(movieName)));
    }
}
