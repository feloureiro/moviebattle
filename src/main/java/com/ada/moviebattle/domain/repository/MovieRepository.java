package com.ada.moviebattle.domain.repository;

import com.ada.moviebattle.domain.entity.Movie;
import com.ada.moviebattle.domain.projections.MoviePairProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {

    @Query(value = "SELECT m1.id as movie1, m2.id as movie2 " +
            "FROM movies m1, movies m2, turns t " +
            "WHERE m1.id != m2.id " +
            "AND NOT (m1.id = t.movie1_id AND m2.id = t.movie2_id OR m1.id = t.movie2_id AND m2.id = t.movie1_id) " +
            "AND t.GAME_ID = :gameId ORDER BY RAND() LIMIT 1;", nativeQuery = true)
    MoviePairProjection findMoviePairProjection(@Param("gameId") UUID gameId);

    @Query(value = "SELECT m1.id as movie1, m2.id as movie2 FROM movies m1, movies m2 WHERE m1.id != m2.id  ORDER BY RAND() LIMIT 1", nativeQuery = true)
    MoviePairProjection findMoviePairProjection();

}
