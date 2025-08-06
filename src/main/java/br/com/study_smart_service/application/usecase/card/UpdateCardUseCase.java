package br.com.study_smart_service.application.usecase.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardDto;

import java.util.UUID;

public interface UpdateCardUseCase {

    CardDto execute(UUID cardId, UUID userId, String frontText, String backText) throws Exception;
}
