package br.com.study_smart_service.application.service.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardSearchDto;
import br.com.study_smart_service.application.usecase.card.SearchCardsUseCase;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SearchCardsServiceImpl implements SearchCardsUseCase {

    static final int MAX_RESULTS = 100;

    private final CardRepository cardRepository;

    public List<CardSearchDto> execute(UUID userId, UUID deckId, String text, String tag) {
        return cardRepository.search(userId, deckId, text, tag, MAX_RESULTS)
                .stream()
                .map(card -> new CardSearchDto(
                        card.getId().toString(),
                        card.getDeck().getId().toString(),
                        card.getDeck().getTitle(),
                        card.getFrontText(),
                        card.getBackText(),
                        card.getTags()))
                .toList();
    }
}
