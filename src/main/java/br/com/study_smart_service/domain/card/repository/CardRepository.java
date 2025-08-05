package br.com.study_smart_service.domain.card.repository;

import br.com.study_smart_service.domain.card.model.Card;

import java.util.List;
import java.util.UUID;

public interface CardRepository {

    Card findById(UUID id);
    List<Card> findAllByDeckIdAndUserId(UUID deckId, UUID userId);
    Card save(Card card);
    void delete(Card card);
}
