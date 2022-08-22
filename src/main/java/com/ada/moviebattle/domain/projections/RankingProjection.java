package com.ada.moviebattle.domain.projections;

import java.math.BigDecimal;

public interface RankingProjection {
    Integer getGames();
    Integer getCorrects();
    Integer getWrongs();
    BigDecimal getPoints();
    byte[] getUserId();
}
