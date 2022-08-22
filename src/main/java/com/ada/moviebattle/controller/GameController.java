package com.ada.moviebattle.controller;

import com.ada.moviebattle.controller.dto.*;
import com.ada.moviebattle.error.ResourceNotFoundException;
import com.ada.moviebattle.security.services.UserDetailsImpl;
import com.ada.moviebattle.service.IGameService;
import com.ada.moviebattle.service.ILoadDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@Tag(name = "GameController", description = "Control all game actions")
public class GameController {
    @Autowired
    private IGameService service;

    @Autowired
    private ILoadDataService loadDataService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "starts a new game", description = "Starts a new game if there is no other game running otherwise return all running game stats", parameters = {
            @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true, description = "Bearer <token>", schema = @Schema(type = "string"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "game started successfully", content = @Content(schema = @Schema(implementation = GameDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthenticated")
    })
    public GameDTO start(Authentication authentication) {
        return service.start((UserDetailsImpl) authentication.getPrincipal());
    }

    @Operation(summary = "return game actual turn", description = "returns the actual open turn if any game running", parameters = {
            @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true, description = "Bearer <token>", schema = @Schema(type = "string"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "game started successfully", content = @Content(schema = @Schema(implementation = GameTurnDTO.class))),
            @ApiResponse(responseCode = "404", description = "no game open found")
    })
    @GetMapping
    public GameTurnDTO getTurn(Authentication authentication) {
        return service.getTurn((UserDetailsImpl) authentication.getPrincipal());
    }

    @Operation(summary = "processes player's answer", description = "processes player's answer", parameters = {
            @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true, description = "Bearer <token>", schema = @Schema(type = "string"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "answers processed", content = @Content(schema = @Schema(implementation = TurnResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "no game open found",  content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "401", description = "Unauthenticated")
    })
    @PutMapping
    public TurnResponseDTO answer(Authentication authentication, @RequestBody TurnDTO answer) {
       return service.answer(answer, (UserDetailsImpl) authentication.getPrincipal());
    }

    @Operation(summary = "finishes game open", description = "finish any current game open", parameters = {
            @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true, description = "Bearer <token>", schema = @Schema(type = "string"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "finishes the game", content = @Content(schema = @Schema(implementation = GameFinishedDTO.class))),
            @ApiResponse(responseCode = "404", description = "no game open found",  content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "401", description = "Unauthenticated")
    })
    @DeleteMapping
    public GameFinishedDTO end(@AuthenticationPrincipal UserDetailsImpl user) {
        return service.end(user);
    }

    @PostMapping("/load-data")
    @Operation(summary = "load data from TMDB and OMDB", description = "get the 200 popular movies and save on database" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data loaded successfully"),
            @ApiResponse(responseCode = "401", description = "missing api keys")
    })
    public ResponseEntity<?> loadData(@RequestBody KeysDTO keys) {
        return loadDataService.loadData(keys);
    }

    @GetMapping("/ranking")
    @Operation(summary = "returns players ranking", description = "get the players ranking" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns ranking"),
    })
    public List<RankingDTO> getRanking() {
        return service.getRanking();
    }
}
