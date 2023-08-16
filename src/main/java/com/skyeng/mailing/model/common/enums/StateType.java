package com.skyeng.mailing.model.common.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Статусы посылки
 * @author vm4tech
 * @since 16.08.2023
 */
public enum StateType {
    @JsonProperty("10")
    CREATED(10),
    @JsonProperty("20")
    IN_PROCESSING(20),
    @JsonProperty("30")
    SENT_TO(30),
    @JsonProperty("40")
    AWAITNG_RECEIPT(40);

//    @JsonProperty("50")
//    RETURNED_BACK(50);

    @Getter
    private final int value;

    StateType(int value){
        this.value = value;
    }

    public static StateType of(int val) throws IllegalArgumentException{
        StateType res = Stream.of(StateType.values())
                .filter(el -> el.getValue() == val)
                .findFirst()
                .orElse(null);
        if (res == null) {
            throw new IllegalArgumentException("Impossible find type of send status by value: " + val);
        }
        return res;
    }
}
