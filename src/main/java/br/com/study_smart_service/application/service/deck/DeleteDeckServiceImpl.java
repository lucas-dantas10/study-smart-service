package br.com.study_smart_service.application.service.deck;

import br.com.study_smart_service.application.usecase.deck.DeleteDeckUseCase;
import br.com.study_smart_service.domain.deck.model.Deck;
import br.com.study_smart_service.domain.deck.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteDeckServiceImpl implements DeleteDeckUseCase {

    private final DeckRepository deckRepository;

    public void execute(UUID deckId, UUID userId) throws Exception {
        Deck deck = deckRepository.findByIdAndUserId(deckId, userId);

        if (deck == null) {
            throw new Exception("Deck não encontrado.");
        }

        deckRepository.delete(deck);
    }
}
