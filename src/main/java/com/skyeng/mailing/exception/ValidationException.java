package com.skyeng.mailing.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseStatus;
@Data
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
    private List<FiledErrorDTO> errors = new ArrayList<>();
    private static String messageError = "Ошибка валидации";
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(FiledErrorDTO errorDTO) {
        this(messageError);
        errors.add(errorDTO);
    }
    public ValidationException(String filed, String message) {
        this(messageError);
        errors.add(new FiledErrorDTO(filed, message));
    }
    public ValidationException(List<FieldError> errors) {
        this(messageError);

        errors.forEach(fieldError -> {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            this.errors.add(new FiledErrorDTO(field, message));
        });
    }

    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
