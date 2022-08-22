package com.ada.moviebattle.service.impl;

import com.ada.moviebattle.controller.dto.KeysDTO;
import com.ada.moviebattle.domain.entity.Movie;
import com.ada.moviebattle.domain.repository.MovieRepository;
import com.ada.moviebattle.feign.OMDBClient;
import com.ada.moviebattle.feign.TMDBClient;
import com.ada.moviebattle.feign.dto.OMDBMovie;
import com.ada.moviebattle.feign.dto.TMBDMovie;
import com.ada.moviebattle.feign.dto.TMDBPopularResponse;
import com.ada.moviebattle.service.ILoadDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class LoadDataService implements ILoadDataService {
    @Autowired
    private TMDBClient tmdbClient;

    @Autowired
    private OMDBClient omdbClient;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public ResponseEntity<?> loadData(KeysDTO keys) {
        for(int i = 1; i < 11; i++) {
            TMDBPopularResponse response = tmdbClient.getPopular(keys.getTmdbKey(), false, false, i);

            response.getResults().stream().forEach(result -> {
                TMBDMovie tmbdMovie = tmdbClient.getMovie(keys.getTmdbKey(), result.getId());

                if(tmbdMovie != null && !StringUtils.isBlank(tmbdMovie.getImdbId())){
                    OMDBMovie omdbMovie = omdbClient.getMovie(keys.getOmdbKey(), tmbdMovie.getImdbId(), "short");
                    Movie movie = new Movie();
                    copyProperties(omdbMovie, movie);
                    treatData(omdbMovie, movie);
                    movieRepository.save(movie);
                }
            });
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void treatData(OMDBMovie omdbMovie, Movie movie) {
        movie.setImdbVotes(omdbMovie.getImdbVotes() == null || omdbMovie.getImdbVotes().equals("N/A") ? 0 : Integer.parseInt(omdbMovie.getImdbVotes().replaceAll(",", "")));
        movie.setImdbRating(omdbMovie.getImdbRating() == null || omdbMovie.getImdbRating().equals("N/A") ? BigDecimal.ZERO : new BigDecimal(omdbMovie.getImdbRating()));
    }

}
