package com.learnwiremock.service;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.learnwiremock.exception.MovieErrorResponse;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class MoviesRestClientServerFaultTest {
    private MoviesRestClient moviesRestClient;
    Options options = wireMockConfig().
            port(8088)
            .notifier(new ConsoleNotifier(true))
            .extensions(new ResponseTemplateTransformer(true));

    private final TcpClient tcpClient = TcpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(5))
                    .addHandlerLast(new WriteTimeoutHandler(5)));

    @Rule
    // this is equivalent of the Wiremock server configuration in Junit 5
    public WireMockRule wireMockRule = new WireMockRule(options);

    @Before
    public void setUp() {
        int port = wireMockRule.port();
        String baseUrl = String.format("http://localhost:%s/", port);
        System.out.println("baseUrl : " + baseUrl);
        //webClient = WebClient.create(baseUrl);
        WebClient webClient = WebClient.builder().clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .baseUrl(baseUrl).build();
        moviesRestClient = new MoviesRestClient(webClient);
    }

    @Test(expected = MovieErrorResponse.class)
    public void retrieveAllMovies() {

        //given
        stubFor(get(anyUrl())
                .willReturn(serverError()));

        //then
        moviesRestClient.retrieveAllMovies();

    }

    @Test(expected = MovieErrorResponse.class)
    public void retrieveAllMovies_503_serviceUnAvailable() {

        //given
        stubFor(get(anyUrl())
                .willReturn(serverError()
                .withStatus(HttpStatus.SERVICE_UNAVAILABLE.value())
                .withBody("Service Unavailable")));

        //then
        moviesRestClient.retrieveAllMovies();


    }

    @Test(expected = MovieErrorResponse.class)
    public void retrieveAllMovies_FaultResponse() {

        //given
        stubFor(get(anyUrl())
                .willReturn(aResponse().withFault(Fault.EMPTY_RESPONSE)));

        //then
        moviesRestClient.retrieveAllMovies();

    }

    @Test(expected = MovieErrorResponse.class)
    public void retrieveAllMovies_RandomDataThenClose() {

        //given
        stubFor(get(anyUrl())
                .willReturn(aResponse().withFault(Fault.RANDOM_DATA_THEN_CLOSE)));

        //then
         moviesRestClient.retrieveAllMovies();

    }

    @Test(expected = MovieErrorResponse.class)
    public void retrieveAllMovies_fixedDelay() {

        //given
        stubFor(get(anyUrl())
                .willReturn(ok().withFixedDelay(10000)));

        //then
        moviesRestClient.retrieveAllMovies();

    }

    @Test(expected = MovieErrorResponse.class)
    public void retrieveAllMovies_RandomDelay() {

        //given
        stubFor(get(anyUrl())
                .willReturn(ok().withUniformRandomDelay(6000,10000)));

        //then
        moviesRestClient.retrieveAllMovies();

    }

}

