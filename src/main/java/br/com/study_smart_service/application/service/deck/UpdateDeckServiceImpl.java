package br.com.study_smart_service.application.service.deck;

import br.com.study_smart_service.adapter.inbound.web.deck.dto.DeckDto;
import br.com.study_smart_service.application.usecase.deck.UpdateDeckUseCase;
import br.com.study_smart_service.domain.deck.model.Deck;
import br.com.study_smart_service.domain.deck.repository.DeckRepository;
import br.com.study_smart_service.domain.user.model.User;
import br.com.study_smart_service.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateDeckServiceImpl implements UpdateDeckUseCase {

    private final DeckRepository deckRepository;

    public DeckDto execute(UUID deckId, UUID userId, String title) throws Exception {
        Deck deck = deckRepository.findByIdAndUserId(deckId, userId);

        if (deck == null) {
            throw new EntityNotFoundException("Deck não encontrado.");
        }

        deck.setTitle(title);
        deck.setUpdatedAt(LocalDateTime.now());

        deckRepository.save(deck);

        return new DeckDto(deck.getId().toString(),
                deck.getTitle(),
                deck.getCreatedAt().toString(),
                deck.getUpdatedAt().toString());
    }
}
