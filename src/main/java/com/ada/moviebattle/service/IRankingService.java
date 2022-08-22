package com.ada.moviebattle.service;

import com.ada.moviebattle.controller.dto.RankingDTO;

import java.util.List;

public interface IRankingService {
    List<RankingDTO> getRanking();
}
