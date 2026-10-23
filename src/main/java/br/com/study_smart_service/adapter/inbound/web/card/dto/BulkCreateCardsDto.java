package br.com.study_smart_service.adapter.inbound.web.card.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record BulkCreateCardsDto(
        @NotBlank(message = "O deck é obrigatório")
        String deckId,
        @NotEmpty(message = "Informe ao menos um card")
        @Size(max = 500, message = "Envie no máximo 500 cards por vez")
        List<@Valid Item> cards
) {

    public record Item(
            @NotBlank(message = "A frente do card é obrigatória")
            @Size(min = 2, max = 255, message = "A frente do card deve ter entre 2 a 255 caracteres")
            String frontText,
            @NotBlank(message = "O verso do card é obrigatório")
            @Size(min = 2, max = 255, message = "O verso do card deve ter entre 2 a 255 caracteres")
            String backText,
            List<String> tags
    ) {
    }
}
