package br.com.study_smart_service.application.usecase.deck;

import br.com.study_smart_service.adapter.inbound.web.deck.dto.DeckDto;

import java.util.UUID;

public interface UpdateDeckUseCase {

    DeckDto execute(UUID deckId, UUID userId, String title) throws Exception;
}
