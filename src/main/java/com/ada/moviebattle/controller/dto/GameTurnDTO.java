package com.ada.moviebattle.controller.dto;

import com.ada.moviebattle.domain.entity.Turn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@Schema(description = "game actual turn")
public class GameTurnDTO {
    @Schema(description = "game internal identification")
    private UUID id;
    @Schema(description = "pair of movies to choose")
    private Turn turn;
    @Schema(description = "remaining remaining mistakes allowed")
    private Integer remainingAttempts;
}
