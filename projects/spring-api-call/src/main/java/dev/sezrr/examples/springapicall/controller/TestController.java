package dev.sezrr.examples.springapicall.controller;

import dev.sezrr.examples.springapicall.config.WebClientConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tests")
public class TestController {
    private final WebClientConfiguration webClientConfiguration;

    @GetMapping
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping(value = "/stream/1", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream() {
        return webClientConfiguration.webclient()
                .get()
                .uri("/stream/1")
                .retrieve()
                .bodyToFlux(String.class)
                .doOnNext(chunk -> System.out.println("Received chunk: " + chunk))
                .doOnError(error -> System.err.println("Error: " + error));
    }

    @GetMapping(value = "/stream/2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamWorks() {
        return webClientConfiguration.webclient()
                .get()
                .uri("/stream/2")
                .retrieve()
                .bodyToFlux(String.class)
                .doOnNext(chunk -> System.out.println("Received chunk: " + chunk))
                .doOnError(error -> System.err.println("Error: " + error));
    }
}
