package br.com.study_smart_service.application.service.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardStudyDto;
import br.com.study_smart_service.adapter.inbound.web.card.dto.NextIntervalsDto;
import br.com.study_smart_service.application.usecase.card.DailyStudyUseCase;
import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import br.com.study_smart_service.domain.review.enums.EQuality;
import br.com.study_smart_service.domain.review.model.Review;
import br.com.study_smart_service.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DailyStudyServiceImpl implements DailyStudyUseCase {

    private final CardRepository cardRepository;
    private final ReviewRepository reviewRepository;

    public List<CardStudyDto> execute(UUID deckId, UUID userId) {
        List<Card> cards = cardRepository.findAllStudyCardsByDeckIdAndUserId(deckId, userId);

        Map<UUID, Review> latestReviews = reviewRepository.findLatestByUserIdAndCardIds(
                userId,
                cards.stream().map(Card::getId).toList()
        );

        return cards.stream()
                .map(card -> new CardStudyDto(
                        card.getId().toString(),
                        card.getFrontText(),
                        card.getBackText(),
                        card.getTags(),
                        nextIntervals(latestReviews.get(card.getId()))))
                .toList();
    }

    private NextIntervalsDto nextIntervals(Review latestReview) {
        // card nunca revisado parte do estado inicial do algoritmo
        Review base = latestReview != null ? latestReview : new Review(null, null);

        return new NextIntervalsDto(
                base.previewIntervalInDays(EQuality.HARD),
                base.previewIntervalInDays(EQuality.MEDIUM),
                base.previewIntervalInDays(EQuality.EASY)
        );
    }
}
