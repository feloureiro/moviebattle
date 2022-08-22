package com.ada.moviebattle.service;

import com.ada.moviebattle.controller.dto.KeysDTO;
import org.springframework.http.ResponseEntity;

public interface ILoadDataService {
    ResponseEntity<?> loadData(KeysDTO keys);

}
