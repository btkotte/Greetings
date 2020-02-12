package com.example.greeting.model;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Objects.isNull;

public enum Account {
    BUSINESS,
    PERSONAL;

    public static Optional<Account> findKey(String key) {
        if (isNull(key)) {
            return Optional.empty();
        }
        return Arrays.stream(Account.values())
                .filter(exampleEnum -> exampleEnum.toString().equals(key.toUpperCase()))
                .findAny();
    }
}