package com.ada.moviebattle.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
@Schema(description = "Authentication response")
public class AuthenticationResponse {
    @Schema(description = "TOKEN needed to start the game")
    private String token;
    private UUID id;
    private String username;
    private List<String> roles;
}
