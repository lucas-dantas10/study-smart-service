package br.com.study_smart_service.domain.review.model;

import java.time.LocalDate;

public record DailyReviewCount(LocalDate date, long count) {
}
