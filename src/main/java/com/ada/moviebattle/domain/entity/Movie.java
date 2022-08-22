package com.ada.moviebattle.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "movies")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String imdbID;

    private BigDecimal imdbRating;

    private Integer imdbVotes;

    private String title;

    private String yearReleased;

    private String genre;

    private String director;

    private String writer;

    private String actors;

    private String awards;

    private String plot;

}
