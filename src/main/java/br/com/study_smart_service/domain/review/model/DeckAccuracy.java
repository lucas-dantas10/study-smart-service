package br.com.study_smart_service.domain.review.model;

import java.util.UUID;

public record DeckAccuracy(UUID deckId, String deckTitle, long reviews, long correct) {
}
