package br.com.study_smart_service.application.service.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardStudyDto;
import br.com.study_smart_service.application.usecase.card.DailyStudyUseCase;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DailyStudyServiceImpl implements DailyStudyUseCase {

    private final CardRepository cardRepository;

    public List<CardStudyDto> execute(UUID deckId, UUID userId) {
        return cardRepository.findAllStudyCardsByDeckIdAndUserId(deckId, userId)
                .stream()
                .map(card -> new CardStudyDto(card.getId().toString(),
                        card.getFrontText(),
                        card.getBackText())
                )
                .toList();
    }
}
