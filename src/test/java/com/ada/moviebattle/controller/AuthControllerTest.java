package com.ada.moviebattle.controller;

import com.ada.moviebattle.controller.dto.AuthenticationRequest;
import com.ada.moviebattle.controller.dto.AuthenticationResponse;
import com.ada.moviebattle.service.impl.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService service;

    @Autowired
    private ObjectMapper objectMapper;
    private AuthenticationRequest authRequest;
    private AuthenticationResponse authResponse;

    @BeforeEach
    void setUp() {
        authRequest = AuthenticationRequest.builder().username("teste").password("pass").build();
        authResponse = AuthenticationResponse.builder().id(UUID.randomUUID()).token("token").build();
    }

    @Test
    void shouldLogin() throws Exception {
        Mockito.when(service.authenticateUser(authRequest)).thenReturn(authResponse);

        mockMvc.perform(post("/auth")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk());
    }
}
