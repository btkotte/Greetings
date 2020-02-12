package com.example.greeting.model;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Objects.isNull;

public enum Type {
    SMALL,
    BIG;

    public static Optional<Type> findKey(String key) {
        if (isNull(key)) {
            return Optional.empty();
        }
        return Arrays.stream(Type.values())
                .filter(exampleEnum -> exampleEnum.toString().equals(key.toUpperCase()))
                .findAny();
    }
}