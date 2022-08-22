package com.ada.moviebattle.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Schema(description = "game resume")
public class GameFinishedDTO {
    @Schema(description = "corrects answers")
    private Integer correctAnswers;
    @Schema(description = "wrong answers")
    private Integer wrongAnswers;
    @Schema(description = "percentage of hits")
    private BigDecimal percentage;
}
