package br.com.study_smart_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class StudySmartServiceApplication {

    private StudySmartServiceApplication() {
    }

    public static void main(final String[] args) {
        SpringApplication.run(StudySmartServiceApplication.class, args);
    }
}
