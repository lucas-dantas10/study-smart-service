package br.com.study_smart_service.application.usecase.deck;

import br.com.study_smart_service.adapter.inbound.web.deck.dto.DeckDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FindAllDeckByUserIdUseCase {

    Page<DeckDto> execute(UUID userId, Pageable pageable);
}
