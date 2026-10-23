package br.com.study_smart_service.adapter.inbound.web.card.dto;

import java.time.LocalDate;
import java.util.List;

public record FindCardDto(
        String id,
        String frontText,
        String backText,
        String createdAt,
        String updatedAt,
        LocalDate nextReviewAt,
        List<String> tags
) {

    public FindCardDto(
            String id,
            String frontText,
            String backText,
            String createdAt,
            String updatedAt,
            LocalDate nextReviewAt
    ) {
        this(id, frontText, backText, createdAt, updatedAt, nextReviewAt, List.of());
    }
}
