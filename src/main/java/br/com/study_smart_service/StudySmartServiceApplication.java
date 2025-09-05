package br.com.study_smart_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StudySmartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudySmartServiceApplication.class, args);
	}

}
