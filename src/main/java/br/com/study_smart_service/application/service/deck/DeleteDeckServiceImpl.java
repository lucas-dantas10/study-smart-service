package br.com.study_smart_service.application.service.deck;

import br.com.study_smart_service.application.usecase.deck.DeleteDeckUseCase;
import br.com.study_smart_service.domain.deck.model.Deck;
import br.com.study_smart_service.domain.deck.repository.DeckRepository;
import br.com.study_smart_service.domain.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteDeckServiceImpl implements DeleteDeckUseCase {

    private final DeckRepository deckRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void execute(UUID deckId, UUID userId) throws Exception {
        Deck deck = deckRepository.findByIdAndUserId(deckId, userId);

        if (deck == null) {
            throw new Exception("Deck não encontrado.");
        }

        deck.getCards().forEach(card -> {
            reviewRepository.deleteByCardId(card.getId());
        });

        deckRepository.delete(deck);
    }
}
