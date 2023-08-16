package com.skyeng.mailing.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FiledErrorDTO {
    private String field;
    private String message;
}
