package com.ada.moviebattle.domain.entity;

import com.ada.moviebattle.domain.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "turns")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Turn {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne
    private Movie movie1;

    @ManyToOne
    private Movie movie2;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JsonIgnore
    private Game game;
}
