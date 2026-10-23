package br.com.study_smart_service.application.usecase.card;

import java.util.List;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardDto;

import java.util.UUID;

public interface CreateCardUseCase {

    CardDto execute(UUID deckId, UUID userId, String frontText, String backText, List<String> tags) throws Exception;
}
