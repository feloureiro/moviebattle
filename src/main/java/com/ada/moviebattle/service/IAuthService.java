package com.ada.moviebattle.service;

import com.ada.moviebattle.controller.dto.AuthenticationRequest;
import com.ada.moviebattle.controller.dto.AuthenticationResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest);
    ResponseEntity<?> registerUser(AuthenticationRequest authenticationRequest);
}
