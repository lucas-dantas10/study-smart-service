package br.com.study_smart_service.application.usecase.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardDto;

import java.util.List;
import java.util.UUID;

public interface FindAllCardsByDeckIdUseCase {

    List<CardDto> execute(UUID deckId, UUID userId);
}
