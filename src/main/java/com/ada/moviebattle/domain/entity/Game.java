package com.ada.moviebattle.domain.entity;

import com.ada.moviebattle.domain.entity.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "games")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;

    private Integer correctAnswers;

    private Integer wrongAnswers;

    @OneToMany(mappedBy = "game", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Turn> turns;

    @Column(columnDefinition = "uuid")
    private UUID userId;

    @Enumerated(EnumType.STRING)
    private Status status;

    public void addTurn(Turn turn) {
        if(this.turns == null)
            turns = new ArrayList<>();
        turns.add(turn);
    }

}
