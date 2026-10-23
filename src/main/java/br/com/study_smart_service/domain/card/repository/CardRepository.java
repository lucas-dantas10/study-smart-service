package br.com.study_smart_service.domain.card.repository;

import br.com.study_smart_service.domain.card.model.Card;

import java.util.List;
import java.util.UUID;

public interface CardRepository {

    Card findById(UUID id);
    Card findByIdAndUserId(UUID id, UUID userId);
    List<Card> findAllByDeckIdAndUserId(UUID deckId, UUID userId);
    List<Card> findAllStudyCardsByDeckIdAndUserId(UUID deckId, UUID userId);
    List<Card> findAllStudyCardsByUserId(UUID userId);
    Card save(Card card);
    void delete(Card card);

    /** Busca textual (frente/verso) e/ou por tag nos cards do usuário; deckId opcional. */
    List<Card> search(UUID userId, UUID deckId, String text, String tag, int limit);

    long countWithoutReviewByUserId(UUID userId);
}
