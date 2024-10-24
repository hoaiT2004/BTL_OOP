package com.example.btl_oop.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.stream.Stream;

public enum StatusEnum {

    Empty(1),
    Non_Empty(2);

    @Getter(onMethod_ = @JsonValue)
    private final Integer value;

    StatusEnum(Integer value) {
        this.value = value;
    }

    public static StatusEnum fromValue(Integer value) {
        return Stream.of(StatusEnum.values())
                .filter(targetEnum -> targetEnum.value.equals(value))
                .findFirst().orElse(null);
    }

}
