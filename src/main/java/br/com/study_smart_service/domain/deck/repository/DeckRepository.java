package br.com.study_smart_service.domain.deck.repository;

import br.com.study_smart_service.domain.deck.model.Deck;

import java.util.List;
import java.util.UUID;

public interface DeckRepository {

    Deck findById(UUID id);
    List<Deck> findAllByUserId(UUID userId);
    Deck save(Deck deck);
    void delete(Deck deck);
}
