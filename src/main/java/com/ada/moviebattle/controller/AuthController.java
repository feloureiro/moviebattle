package com.ada.moviebattle.controller;

import com.ada.moviebattle.controller.dto.AuthenticationRequest;
import com.ada.moviebattle.controller.dto.AuthenticationResponse;
import com.ada.moviebattle.error.ResourceConflictException;
import com.ada.moviebattle.service.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "Creates and authorizes users")
public class AuthController {
    @Autowired
    private IAuthService service;

    @PostMapping
    @Operation(summary = "Authenticate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful authentication", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "failed authentication")
    })
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse response = service.authenticateUser(authenticationRequest);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Register user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful sign-up", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "409", description = "failed sign-up", content = @Content(schema = @Schema(implementation = ResourceConflictException.class)))
    })
    public ResponseEntity<?> registerUser(@RequestBody AuthenticationRequest authenticationRequest) {
        return service.registerUser(authenticationRequest);
    }
}
