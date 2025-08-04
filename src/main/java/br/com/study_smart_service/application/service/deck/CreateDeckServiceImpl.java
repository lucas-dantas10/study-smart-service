package br.com.study_smart_service.application.service.deck;

import br.com.study_smart_service.application.usecase.deck.CreateDeckUseCase;
import br.com.study_smart_service.adapter.inbound.web.deck.dto.DeckDto;
import br.com.study_smart_service.domain.deck.model.Deck;
import br.com.study_smart_service.domain.deck.repository.DeckRepository;
import br.com.study_smart_service.domain.user.model.User;
import br.com.study_smart_service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateDeckServiceImpl implements CreateDeckUseCase {

    private final UserRepository userRepository;
    private final DeckRepository deckRepository;

    public DeckDto execute(UUID userId, String title) {
        User user = userRepository.findById(userId);

        Deck deck = new Deck(user, title);

        deck = deckRepository.save(deck);

        return new DeckDto(deck.getId().toString(),
            deck.getTitle(),
            deck.getCreatedAt().toString(),
            null
        );
    }
}
