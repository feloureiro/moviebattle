package com.ada.moviebattle;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info = @Info(title = "Movies Battle", version = "3.0", description = "API for a movie battle game"))
public class MoviebattleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviebattleApplication.class, args);
	}

}
