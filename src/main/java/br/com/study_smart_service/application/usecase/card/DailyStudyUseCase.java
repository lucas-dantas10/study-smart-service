package br.com.study_smart_service.application.usecase.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardStudyDto;

import java.util.List;
import java.util.UUID;

public interface DailyStudyUseCase {

    List<CardStudyDto> execute(UUID deckId, UUID userId);
}
