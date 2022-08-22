package com.ada.moviebattle.service.impl;

import com.ada.moviebattle.controller.dto.*;
import com.ada.moviebattle.domain.entity.Game;
import com.ada.moviebattle.domain.entity.Turn;
import com.ada.moviebattle.domain.entity.enums.Status;
import com.ada.moviebattle.domain.repository.GameRepository;
import com.ada.moviebattle.domain.repository.TurnRepository;
import com.ada.moviebattle.error.ResourceBadRequestException;
import com.ada.moviebattle.error.ResourceNotFoundException;
import com.ada.moviebattle.error.ResponseErrorEnum;
import com.ada.moviebattle.security.services.UserDetailsImpl;
import com.ada.moviebattle.service.IGameService;
import com.ada.moviebattle.service.IRankingService;
import com.ada.moviebattle.service.ITurnService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class GameService implements IGameService {

    @Autowired
    private GameRepository repository;

    @Autowired
    private TurnRepository turnRepository;

    @Autowired
    private IRankingService rankingService;

    @Autowired
    private ITurnService turnService;
    @Override
    public GameDTO start(UserDetailsImpl user) {
        GameDTO response = new GameDTO();
        Game game = repository.findByUserIdAndStatus(user.getId(), Status.OPEN).orElseGet(() -> {

            Game newGame = Game.builder()
                    .userId(user.getId())
                    .correctAnswers(0)
                    .wrongAnswers(0)
                    .status(Status.OPEN).build();
            newGame.setTurns(Arrays.asList(turnService.generateTurn(newGame)));
            return repository.save(newGame);
        });
        copyProperties(game, response);
        return response;
    }

    @Override
    @Transactional
    public GameFinishedDTO end(UserDetailsImpl user) {
        Game game = repository.findByUserIdAndStatus(user.getId(), Status.OPEN).orElseThrow(() -> new ResourceBadRequestException(ResponseErrorEnum.NO_GAME_STARTED));
        game.setStatus(Status.CLOSED);
        repository.save(game);

        return GameFinishedDTO
                .builder()
                .correctAnswers(game.getCorrectAnswers())
                .wrongAnswers(game.getWrongAnswers())
                .percentage(calculatePercentage(game)).build();
    }

    @Override
    public TurnResponseDTO answer(TurnDTO answer, UserDetailsImpl user) {
        TurnResponseDTO response = TurnResponseDTO.builder().gameOver(false).build();

        Game game = repository.findByUserIdAndStatus(user.getId(), Status.OPEN).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
        Turn openTurn = game.getTurns().stream().filter(turn -> turn.getStatus().equals(Status.OPEN)).findFirst().get();

        BigDecimal movie1Points = openTurn.getMovie1().getImdbRating().multiply(new BigDecimal(openTurn.getMovie1().getImdbVotes().toString()));
        BigDecimal movie2Points = openTurn.getMovie2().getImdbRating().multiply(new BigDecimal(openTurn.getMovie2().getImdbVotes().toString()));

        if((movie1Points.compareTo(movie2Points) >= 0 && answer.getMovieId().equals(openTurn.getMovie1().getId()))
                || movie1Points.compareTo(movie2Points) <= 0 && answer.getMovieId().equals(openTurn.getMovie2().getId())){
            game.setCorrectAnswers(game.getCorrectAnswers() + 1);
            game.addTurn(turnService.generateTurn(game));

            response.setIsCorrect(true);
            openTurn.setStatus(Status.CLOSED);
            repository.save(game);
        }
        else if(game.getWrongAnswers() < 3) {
            response.setIsCorrect(false);

            game.setWrongAnswers(game.getWrongAnswers() + 1);
            game.addTurn(turnService.generateTurn(game));
            repository.save(game);
        } else {
                end(user);
                response.setGameOver(true);
        }
        openTurn.setStatus(Status.CLOSED);

        repository.save(game);
        return response;
    }

    @Override
    public GameTurnDTO getTurn(UserDetailsImpl user) {
        Game game = repository.findByUserIdAndStatus(user.getId(), Status.OPEN).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
        Turn turn = turnRepository.findByGameAndStatus(game, Status.OPEN).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
        return GameTurnDTO.builder().id(game.getId()).turn(turn).remainingAttempts(3 - game.getWrongAnswers()).build();
    }

    @Override
    public List<RankingDTO> getRanking() {
        return rankingService.getRanking();
    }

    private BigDecimal calculatePercentage(Game game) {
        BigDecimal correct = new BigDecimal(game.getCorrectAnswers());
        BigDecimal wrong = new BigDecimal(game.getWrongAnswers());
        BigDecimal all = correct.add(wrong);
        if(all.equals(BigDecimal.ZERO))
            return BigDecimal.ZERO;
        return correct.divide(all).multiply(new BigDecimal("100"));
    }

}
