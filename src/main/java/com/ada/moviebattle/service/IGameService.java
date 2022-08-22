package com.ada.moviebattle.service;

import com.ada.moviebattle.controller.dto.*;
import com.ada.moviebattle.security.services.UserDetailsImpl;

import java.util.List;

public interface IGameService {

    GameDTO start(UserDetailsImpl user);

    GameFinishedDTO end(UserDetailsImpl user);

    TurnResponseDTO answer(TurnDTO answer, UserDetailsImpl user);

    GameTurnDTO getTurn(UserDetailsImpl principal);

    List<RankingDTO> getRanking();
}
