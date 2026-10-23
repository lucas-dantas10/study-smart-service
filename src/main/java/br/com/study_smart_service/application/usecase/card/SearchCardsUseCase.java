package br.com.study_smart_service.application.usecase.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardSearchDto;

import java.util.List;
import java.util.UUID;

public interface SearchCardsUseCase {

    List<CardSearchDto> execute(UUID userId, UUID deckId, String text, String tag);
}
