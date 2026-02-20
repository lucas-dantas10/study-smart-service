package br.com.study_smart_service.adapter.inbound.web.deck.dto;

import jakarta.annotation.Nullable;

public record DeckDto(
        String id,
        String title,
        String createdAt,
        @Nullable
        String updatedAt) {
}
