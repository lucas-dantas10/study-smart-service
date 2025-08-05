package br.com.study_smart_service.adapter.inbound.web.card.dto;

public record CreateCardDto(
        String deckId,
        String frontText,
        String backText
) {
}
