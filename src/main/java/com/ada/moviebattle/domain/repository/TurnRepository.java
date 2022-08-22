package com.ada.moviebattle.domain.repository;

import com.ada.moviebattle.domain.entity.Game;
import com.ada.moviebattle.domain.entity.Turn;

import com.ada.moviebattle.domain.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TurnRepository extends JpaRepository<Turn, UUID> {
    Optional<Turn> findByGameAndStatus(Game game, Status open);
}
