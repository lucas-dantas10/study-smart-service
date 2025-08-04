package br.com.study_smart_service.domain.deck.repository;

import br.com.study_smart_service.domain.deck.model.Deck;

import java.util.List;
import java.util.UUID;

public interface DeckRepository {

    Deck findById(UUID id);
    Deck findByIdAndUserId(UUID id, UUID userId);
    List<Deck> findAllByUserId(UUID userId);
    Deck save(Deck deck);
    void delete(Deck deck);
}
