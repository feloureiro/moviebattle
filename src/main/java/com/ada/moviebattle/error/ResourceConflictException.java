package com.ada.moviebattle.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException {

    private ResponseErrorEnum errorEnum;
    public ResourceConflictException(ResponseErrorEnum error) {
        super(error.getText());
        this.errorEnum = error;
    }
}
