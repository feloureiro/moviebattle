package com.ada.moviebattle.domain.repository;

import com.ada.moviebattle.domain.entity.Game;
import com.ada.moviebattle.domain.entity.enums.Status;
import com.ada.moviebattle.domain.projections.RankingProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
    Optional<Game> findByUserIdAndStatus(UUID userId, Status status);

    @Query(value = "SELECT " +
            "g.user_id as userId, " +
            "COUNT(g.id) as games, " +
            "SUM(g.correct_answers) corrects, " +
            "SUM(g.wrong_answers) wrongs, " +
            "CAST(SUM(g.correct_answers) as float) / CAST((SUM(g.correct_answers) + SUM(g.wrong_answers)) as float) * 100 * CAST(COUNT(g.id) as float) as points, g.user_id " +
            "FROM games g  " +
            "GROUP BY g.user_id " +
            "ORDER BY points DESC;", nativeQuery = true)
    List<RankingProjection> getRanking();



}
