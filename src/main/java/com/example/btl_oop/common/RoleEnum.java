package com.example.btl_oop.common;

import com.example.btl_oop.entity.Role;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.stream.Stream;

public enum RoleEnum {

    Tenant(1),
    Landlord(2);

    @Getter(onMethod_ = @JsonValue)
    private final Integer value;

    RoleEnum(Integer value) {
        this.value = value;
    }

    public static RoleEnum fromValue(Integer value) {
        return Stream.of(RoleEnum.values())
                .filter(targetEnum -> targetEnum.value.equals(value))
                .findFirst().orElse(null);
    }

}
