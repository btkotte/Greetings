package com.example.greeting.api;


import com.example.greeting.exception.InvalidRequestException;
import com.example.greeting.model.Account;
import com.example.greeting.model.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.example.greeting.model.Type.BIG;
import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController()
@Validated
@RequestMapping(value = "greeting", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GreetingController {

    @GetMapping
    public Mono<String> greetings(@RequestParam String account,
                                  @RequestParam(required = false) String type,
                                  @RequestParam(required = false) Long id) {

        return Mono.fromCallable(() -> {
            Account inputAccount = Account.findKey(account)
                    .orElseThrow(() -> new InvalidRequestException("Invalid account param in the url: " + account));

            switch (inputAccount) {
                case PERSONAL:
                    if (isNull(id) || id < 0) {
                        throw new InvalidRequestException("Id is required in the url and should be greater than 0");
                    }
                    return "Hi, userId " + id;

                case BUSINESS:
                    Type inputType = Type.findKey(type)
                            .orElseThrow(() -> new InvalidRequestException("Invalid type param in the url: " + type));

                    if (inputType.equals(BIG)) {
                        return "Welcome, business user!";
                    }
                    throw new InvalidRequestException("The path is not yet implemented");
            }
            return "The path is not yet implemented";
        });
    }

}

