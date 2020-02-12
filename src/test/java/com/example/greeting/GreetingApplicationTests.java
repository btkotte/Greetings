package com.example.greeting;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "30000")
public class GreetingApplicationTests {

    @Autowired
    private WebTestClient webTestClient;


    @Test
    public void getGreeting_ValidaAccountAndId_Then200Response() {
        createRequestForPersonalAccount("personal", "123")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals("Hi, userId 123");
    }

    @Test
    public void getGreeting_InValidaAccountAndId_Then400Response() {
        createRequestForPersonalAccount("junk", "123")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("message").isEqualTo("Invalid account param in the url: junk");
    }

    @Test
    public void getGreeting_ValidaAccountAndInvalidId_Then400Response() {
        createRequestForPersonalAccount("personal", "abc")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void getGreeting_ValidaAccountAndType_Then200Response() {
        createRequestForBusinessAccount("business", "big")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals("Welcome, business user!");
    }

    @Test
    public void getGreeting_ValidaAccountAndInvalidType_Then400Response() {
        createRequestForBusinessAccount("business", "junk")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("message").isEqualTo("Invalid type param in the url: junk");
    }

    @Test
    public void getGreeting_ValidaAccountAndIncorrectType_Then400Response() {
        createRequestForBusinessAccount("business", "small")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("message").isEqualTo("The path is not yet implemented");
    }

    private WebTestClient.RequestHeadersSpec<?> createRequestForPersonalAccount(String account, String id) {
        return webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/greeting")
                        .queryParam("account", account)
                        .queryParam("id", id)
                        .build());
    }

    private WebTestClient.RequestHeadersSpec<?> createRequestForBusinessAccount(String account, String type) {
        return webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/greeting")
                        .queryParam("account", account)
                        .queryParam("type", type)
                        .build());
    }

}
