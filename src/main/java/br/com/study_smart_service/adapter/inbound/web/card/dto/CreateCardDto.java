package br.com.study_smart_service.adapter.inbound.web.card.dto;

import java.util.List;

import jakarta.validation.constraints.Size;

public record CreateCardDto(
        String deckId,
        @Size(min = 2, max = 255, message = "A frente do card deve ter entre 2 a 255 caracteres")
        String frontText,
        @Size(min = 2, max = 255, message = "O verso do card deve ter entre 2 a 255 caracteres")
        String backText,
        List<String> tags
) {

    public CreateCardDto(String deckId, String frontText, String backText) {
        this(deckId, frontText, backText, null);
    }
}
