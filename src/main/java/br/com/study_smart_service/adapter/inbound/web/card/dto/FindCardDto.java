package br.com.study_smart_service.adapter.inbound.web.card.dto;

import java.time.LocalDate;

public record FindCardDto(
        String id,
        String frontText,
        String backText,
        String createdAt,
        String updatedAt,
        LocalDate nextReviewAt
) {
}
