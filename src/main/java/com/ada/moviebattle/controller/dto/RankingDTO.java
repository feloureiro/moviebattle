package com.ada.moviebattle.controller.dto;

import com.ada.moviebattle.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Schema(description = "players ranking")
public class RankingDTO {
    @Schema(description = "user")
    private User user;
    @Schema(description = "all games played and finished")
    private Integer gamesPlayed;
    @Schema(description = "points from all games")
    private BigDecimal points;
}
