package br.com.study_smart_service.application.usecase.deck;

import br.com.study_smart_service.domain.deck.dto.DeckDto;

import java.util.UUID;

public interface CreateDeckUseCase {

    DeckDto execute(UUID userId, String title);
}
