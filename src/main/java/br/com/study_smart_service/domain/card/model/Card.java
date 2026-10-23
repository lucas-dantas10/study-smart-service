package br.com.study_smart_service.domain.card.model;

import br.com.study_smart_service.domain.deck.model.Deck;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Card {

    public static final int MAX_TAGS = 8;
    public static final int MAX_TAG_LENGTH = 30;

    private UUID id;
    private Deck deck;
    private String frontText;
    private String backText;
    private List<String> tags = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Card() {
    }

    public Card(UUID id, String frontText, String backText, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.frontText = frontText;
        this.backText = backText;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Card(Deck deck, String frontText, String backText, List<String> tags) {
        this(deck, frontText, backText);
        this.tags = normalizeTags(tags);
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = normalizeTags(tags);
    }

    public static List<String> normalizeTags(List<String> rawTags) {
        Set<String> normalized = new LinkedHashSet<>();

        if (rawTags == null) {
            return new ArrayList<>();
        }

        for (String raw : rawTags) {
            if (raw == null) {
                continue;
            }

            String tag = raw.trim().toLowerCase().replaceAll("\\s+", "-").replaceAll("[^\\p{L}\\p{N}-]", "");

            if (tag.length() > MAX_TAG_LENGTH) {
                tag = tag.substring(0, MAX_TAG_LENGTH);
            }

            if (!tag.isEmpty()) {
                normalized.add(tag);
            }

            if (normalized.size() == MAX_TAGS) {
                break;
            }
        }

        return new ArrayList<>(normalized);
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
