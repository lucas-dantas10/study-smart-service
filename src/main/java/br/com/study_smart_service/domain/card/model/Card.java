package br.com.study_smart_service.domain.card.model;

import br.com.study_smart_service.domain.deck.model.Deck;

import java.time.LocalDateTime;
import java.util.UUID;

public class Card {

    private UUID id;
    private Deck deck;
    private String frontText;
    private String backText;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Card() {
    }

    public Card(Deck deck, String frontText, String backText) {
        this.deck = deck;
        this.frontText = frontText;
        this.backText = backText;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public String getFrontText() {
        return frontText;
    }

    public void setFrontText(String frontText) {
        this.frontText = frontText;
    }

    public String getBackText() {
        return backText;
    }

    public void setBackText(String backText) {
        this.backText = backText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
