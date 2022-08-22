package com.ada.moviebattle.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "keys from external apis")
public class KeysDTO {
    private String omdbKey;
    private String tmdbKey;
}
