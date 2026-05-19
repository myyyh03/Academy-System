package com.mo7s.academySystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AcademySystem {

	public static void main(String[] args) {
		SpringApplication.run(AcademySystem.class, args);
	}
}
