package com.ada.moviebattle.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class TMBDMovie {
    private BigInteger id;

    @JsonProperty("imdb_id")
    private String imdbId;
}
