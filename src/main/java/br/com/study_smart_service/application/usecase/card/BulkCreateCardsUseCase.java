package br.com.study_smart_service.application.usecase.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.BulkCreateCardsDto;

import java.util.UUID;

public interface BulkCreateCardsUseCase {

    int execute(UUID userId, BulkCreateCardsDto request) throws Exception;
}
