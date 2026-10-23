package br.com.study_smart_service.adapter.inbound.web.card.dto;

import java.util.List;

public record CardSearchDto(
        String id,
        String deckId,
        String deckTitle,
        String frontText,
        String backText,
        List<String> tags
) {
}
