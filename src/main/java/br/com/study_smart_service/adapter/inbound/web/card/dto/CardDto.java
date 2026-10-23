package br.com.study_smart_service.adapter.inbound.web.card.dto;

import java.util.List;

public record CardDto(
        String id,
        String frontText,
        String backText,
        String createdAt,
        String updatedAt,
        List<String> tags
) {

    public CardDto(String id, String frontText, String backText, String createdAt, String updatedAt) {
        this(id, frontText, backText, createdAt, updatedAt, List.of());
    }
}
