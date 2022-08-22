package com.ada.moviebattle.controller.dto;

import com.ada.moviebattle.domain.entity.Turn;
import com.ada.moviebattle.domain.entity.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GameDTO {

    private UUID id;
    private Integer correctAnswers;
    private Integer wrongAnswers;
    private List<Turn> turns;
    private UUID userId;
    private Status status;
}
