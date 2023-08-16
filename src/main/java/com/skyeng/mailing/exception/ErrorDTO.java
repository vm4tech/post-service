package com.skyeng.mailing.exception;

import java.util.List;

import lombok.Data;
@Data
public class ErrorDTO {
    private String status;
    private String message;
    private List<FiledErrorDTO> errors;
    private String time;
}