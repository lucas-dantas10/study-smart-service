package br.com.study_smart_service.application.usecase.deck;

import java.util.UUID;

public interface DeleteDeckUseCase {

    void execute(UUID deckId, UUID userId) throws Exception;
}
