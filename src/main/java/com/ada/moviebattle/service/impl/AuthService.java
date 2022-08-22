package com.ada.moviebattle.service.impl;

import com.ada.moviebattle.controller.dto.AuthenticationRequest;
import com.ada.moviebattle.controller.dto.AuthenticationResponse;
import com.ada.moviebattle.domain.entity.User;
import com.ada.moviebattle.domain.entity.enums.Role;
import com.ada.moviebattle.domain.repository.UserRepository;
import com.ada.moviebattle.error.ResponseErrorEnum;
import com.ada.moviebattle.security.jwt.JwtUtils;
import com.ada.moviebattle.security.services.UserDetailsImpl;
import com.ada.moviebattle.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class AuthService implements IAuthService {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository repository;


    public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());

        return new AuthenticationResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles);
    }

    public ResponseEntity<?> registerUser(AuthenticationRequest authenticationRequest) {
        if(repository.existsByUsername(authenticationRequest.getUsername()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseErrorEnum.AUTH001.getText());

        User user = new User(authenticationRequest.getUsername(),
                bCryptPasswordEncoder.encode(authenticationRequest.getPassword()), Role.ROLE_USER);

        repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }
}
