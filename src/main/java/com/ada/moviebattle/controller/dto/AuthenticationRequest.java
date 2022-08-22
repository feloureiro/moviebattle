package com.ada.moviebattle.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@Schema(description = "Authentication DTO")
public class AuthenticationRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
