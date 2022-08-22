package com.ada.moviebattle.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "result of the player's turn answer")
public class TurnResponseDTO {
    @Schema(description = "indicates if the answer was correct")
    private Boolean isCorrect;

    @Schema(description = "indicates if all 3 chances was used")
    private Boolean gameOver;
}
