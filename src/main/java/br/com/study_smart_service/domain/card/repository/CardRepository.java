package br.com.study_smart_service.domain.card.repository;

import br.com.study_smart_service.domain.card.model.Card;

import java.util.UUID;

public interface CardRepository {

    Card findById(UUID id);
    Card findAllByDeckIdAndUserId(UUID deckId, UUID userId);
    Card save(Card deck);
    void delete(Card deck);
}
