package br.com.study_smart_service.adapter.outbound.card.repository;

import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.card.repository.CardRepository;

import java.util.UUID;

public class CardRepositoryImpl implements CardRepository {

    public Card findById(UUID id) {
        return null;
    }

    public Card findAllByDeckIdAndUserId(UUID deckId, UUID userId) {
        return null;
    }

    public Card save(Card deck) {
        return null;
    }

    public void delete(Card deck) {

    }
}
