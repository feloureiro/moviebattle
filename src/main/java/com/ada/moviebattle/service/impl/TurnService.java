package com.ada.moviebattle.service.impl;

import com.ada.moviebattle.domain.entity.Game;
import com.ada.moviebattle.domain.entity.Movie;
import com.ada.moviebattle.domain.entity.Turn;
import com.ada.moviebattle.domain.entity.enums.Status;
import com.ada.moviebattle.domain.projections.MoviePairProjection;
import com.ada.moviebattle.domain.repository.MovieRepository;
import com.ada.moviebattle.service.ITurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.UUID;

@Service
public class TurnService implements ITurnService {
    @Autowired
    private MovieRepository movieRepository;

    public Turn generateTurn(Game game) {
        MoviePairProjection turnProjection = null;

        if(game.getId() == null)
            turnProjection = movieRepository.findMoviePairProjection();
        else {
            turnProjection = movieRepository.findMoviePairProjection(game.getId());
        }
        Movie movie1 = movieRepository.findById(getUUIDFromBytes(turnProjection.getMovie1())).get();
        Movie movie2 = movieRepository.findById(getUUIDFromBytes(turnProjection.getMovie2())).get();
        return Turn.builder()
                .movie1(movie1)
                .movie2(movie2)
                .status(Status.OPEN)
                .game(game)
                .build();
    }

    public static UUID getUUIDFromBytes(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        Long high = byteBuffer.getLong();
        Long low = byteBuffer.getLong();

        return new UUID(high, low);
    }
}
