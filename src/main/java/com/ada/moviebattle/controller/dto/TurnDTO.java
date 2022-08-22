package com.ada.moviebattle.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "players turn answers")
public class TurnDTO {
    @Schema(description = "movie internal identification for checking if its correct")
    private UUID movieId;
}
