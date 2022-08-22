package com.ada.moviebattle.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceBadRequestException extends RuntimeException {
    private ResponseErrorEnum errorEnum;
    public ResourceBadRequestException(ResponseErrorEnum error) {
        super(error.getText());
        this.errorEnum = error;
    }
}
