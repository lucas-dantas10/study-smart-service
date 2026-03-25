package br.com.study_smart_service.domain.deck.repository;

import br.com.study_smart_service.domain.deck.model.Deck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DeckRepository {

    Deck findById(UUID id);
    Deck findByIdAndUserId(UUID id, UUID userId);
    Page<Deck> findAllByUserId(UUID userId, Pageable pageable);
    Deck save(Deck deck);
    void delete(Deck deck);
}
