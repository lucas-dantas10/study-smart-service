package br.com.study_smart_service.application.service.deck;

import br.com.study_smart_service.adapter.inbound.web.deck.dto.DeckDto;
import br.com.study_smart_service.application.usecase.deck.FindAllDeckByUserIdUseCase;
import br.com.study_smart_service.domain.deck.model.Deck;
import br.com.study_smart_service.domain.deck.repository.DeckRepository;
import br.com.study_smart_service.utils.mapper.deck.DeckMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindAllDeckByUserIdServiceImpl implements FindAllDeckByUserIdUseCase {

    private final DeckRepository deckRepository;
    private final DeckMapper deckMapper;

    public Page<DeckDto> execute(UUID userId, Pageable pageable) {
        Page<Deck> decks = deckRepository.findAllByUserId(userId, pageable);

        return decks.map(deckMapper::domainToDto);
    }
}
