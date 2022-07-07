package com.example.TripleReview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TripleReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripleReviewApplication.class, args);
	}

}
