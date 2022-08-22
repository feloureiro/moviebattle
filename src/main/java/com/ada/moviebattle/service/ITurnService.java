package com.ada.moviebattle.service;

import com.ada.moviebattle.domain.entity.Game;
import com.ada.moviebattle.domain.entity.Turn;

public interface ITurnService {
    Turn generateTurn(Game game);
}
