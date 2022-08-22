package com.ada.moviebattle.feign;

import com.ada.moviebattle.feign.dto.TMBDMovie;
import com.ada.moviebattle.feign.dto.TMDBPopularResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

@FeignClient(name = "tmdbClient", url = "${movie-battle.tmdb.url}")
public interface TMDBClient {

    @GetMapping("/movie/popular")
    TMDBPopularResponse getPopular(@RequestParam("api_key") String apiKey,
                                   @RequestParam("include_adult") Boolean includeAdult,
                                   @RequestParam("include_video") Boolean includeVideo,
                                   @RequestParam(required = false, name = "page") Integer page);

    @GetMapping("/movie/{id}")
    TMBDMovie getMovie(@RequestParam("api_key") String apiKey, @PathVariable("id") BigInteger id);

}
