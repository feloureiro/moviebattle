package com.ada.moviebattle.feign;

import com.ada.moviebattle.feign.dto.OMDBMovie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "omdbClient", url = "${movie-battle.omdb.url}")
public interface OMDBClient {
    @GetMapping
    OMDBMovie getMovie(@RequestParam("apiKey") String apiKey,
                       @RequestParam("i") String imdbId,
                       @RequestParam("plot") String plot);

}
