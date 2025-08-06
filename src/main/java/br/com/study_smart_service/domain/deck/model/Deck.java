package br.com.study_smart_service.domain.deck.model;

import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Deck {

    private UUID id;
    private User user;
    private String title;
    private List<Card> cards;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Deck() {
    }

    public Deck(User user, String title) {
        this.user = user;
        this.title = title;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public Deck update(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();

        return this;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
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
