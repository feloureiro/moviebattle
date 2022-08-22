package com.ada.moviebattle.feign.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TMDBPopularResponse {
    private Integer page;
    private List<TMDBPopularResults> results;
}
