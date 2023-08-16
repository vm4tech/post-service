package com.skyeng.mailing.model.common.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


/**
 * Тип почтового отправления
 * @author vm4tech
 * @since 16.08.2023
 */
public enum MailType {
    @JsonProperty("10")
    LETTER(10),
    @JsonProperty("20")
    PACKAGE(20),
    @JsonProperty("30")
    PARCEL(30),
    @JsonProperty("40")
    POSTCARD(40);

    @Getter
    private final int value;

    MailType(int value){
        this.value = value;
    }

    public static MailType of(int val) throws IllegalArgumentException{
        MailType res = Stream.of(MailType.values())
                .filter(el -> el.getValue() == val)
                .findFirst()
                .orElse(null);
        if (res == null) {
            throw new IllegalArgumentException("Impossible find type of mail type by value: " + val);
        }
        return res;
    }
}
