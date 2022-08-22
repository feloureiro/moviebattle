package com.ada.moviebattle.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private ResponseErrorEnum errorEnum;
    public ResourceNotFoundException(ResponseErrorEnum error) {
        super(error.getText());
        this.errorEnum = error;
    }
}
