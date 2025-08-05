package br.com.study_smart_service.adapter.inbound.web.card.dto;

public record CardDto(
        String id,
        String frontText,
        String backText,
        String createdAt,
        String updatedAt
) {
}
