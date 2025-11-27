package br.com.study_smart_service.application.service.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.FindCardDto;
import br.com.study_smart_service.application.usecase.card.FindAllCardsByDeckIdUseCase;
import br.com.study_smart_service.application.usecase.review.FindRecentReviewByCardIdUseCase;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import br.com.study_smart_service.domain.review.model.Review;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAllCardsByDeckIdServiceImpl implements FindAllCardsByDeckIdUseCase {

    private final CardRepository cardRepository;
    private final FindRecentReviewByCardIdUseCase findRecentReviewByCardIdUseCase;

    public List<FindCardDto> execute(UUID deckId, UUID userId) {
        return cardRepository.findAllByDeckIdAndUserId(deckId, userId)
                .stream()
                .map(card -> {
                    Review review = findRecentReviewByCardIdUseCase.execute(card.getId());

                    return new FindCardDto(
                            card.getId().toString(),
                            card.getFrontText(),
                            card.getBackText(),
                            card.getCreatedAt().toString(),
                            card.getUpdatedAt() != null ? card.getUpdatedAt().toString() : null,
                            review != null ? review.getNextReviewAt() : null);
                })
                .toList();
    }
}
