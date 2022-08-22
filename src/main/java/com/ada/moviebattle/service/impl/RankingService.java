package com.ada.moviebattle.service.impl;

import com.ada.moviebattle.controller.dto.RankingDTO;
import com.ada.moviebattle.domain.projections.RankingProjection;
import com.ada.moviebattle.domain.repository.GameRepository;
import com.ada.moviebattle.domain.repository.UserRepository;
import com.ada.moviebattle.service.IRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RankingService implements IRankingService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;
    @Override
    public List<RankingDTO> getRanking() {
        List<RankingDTO> ranking = new ArrayList<>();
        List<RankingProjection> projections = gameRepository.getRanking();

        projections.forEach(projection -> ranking.add(RankingDTO.builder()
                .user(userRepository.findById(TurnService.getUUIDFromBytes(projection.getUserId())).get())
                .gamesPlayed(projection.getGames())
                .points(projection.getPoints()).build()));

        return ranking;
    }
}
