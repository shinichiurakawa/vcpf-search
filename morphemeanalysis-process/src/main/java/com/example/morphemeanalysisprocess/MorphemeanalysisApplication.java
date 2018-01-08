package com.example.morphemeanalysisprocess;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource("beans-morphemeanalysis-service.xml")
public class MorphemeanalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MorphemeanalysisApplication.class, args);
	}

}
