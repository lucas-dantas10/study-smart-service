package br.com.study_smart_service.domain.review.model;

import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.review.enums.EQuality;
import br.com.study_smart_service.domain.user.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Review {

    private static final double PENALTY_BASE = 0.8;
    private static final double REWARD_PER_QUALITY = 0.28;
    private static final double QUADRATIC_PENALTY = 0.02;
    private static final double LOWER_LIMIT = 1.3;

    private UUID id;
    private User user;
    private Card card;
    private LocalDate nextReviewAt;
    private Integer interval;
    private Integer repetition;
    private Double easiness;
    private Integer lastQuality;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Review(User user, Card card) {
        this.user = user;
        this.card = card;
        this.repetition = 0;
        this.interval = 1;
        this.easiness = 2.5;
        this.nextReviewAt = LocalDate.now();
    }

    public Review createNewReview(LocalDate nextReviewAt,
                  Integer interval,
                  Integer repetition,
                  Double easiness,
                  Integer lastQuality) {
        this.nextReviewAt = nextReviewAt;
        this.interval = interval;
        this.repetition = repetition;
        this.easiness = easiness;
        this.lastQuality = lastQuality;
        this.createdAt = LocalDateTime.now();

        return this;
    }

    public void review(EQuality quality) {
        lastQuality = quality.getQuality();

        if (quality.isDifficult()) {
            repetition = 0;
            interval = 1;

            calculateEasinessAndNextReview();

            return;
        }

        repetition++;

        switch (repetition) {
            case 1:
                interval = 1;
                break;
            case 2:
                interval = 3;
                break;
            default:
                interval = Math.toIntExact(Math.round(interval * easiness));
        }

        calculateEasinessAndNextReview();
    }

    private void calculateEasinessAndNextReview() {
        easiness += formulaEasiness();

        if (easiness < LOWER_LIMIT) {
            easiness = LOWER_LIMIT;
        }

        nextReviewAt = LocalDate.now().plusDays(interval);
    }

    private Double formulaEasiness() {
        double qualityReward = REWARD_PER_QUALITY * lastQuality;
        double qualityPenalty = QUADRATIC_PENALTY * lastQuality * lastQuality;

        return -PENALTY_BASE + qualityReward - qualityPenalty;
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

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public LocalDate getNextReviewAt() {
        return nextReviewAt;
    }

    public void setNextReviewAt(LocalDate nextReviewAt) {
        this.nextReviewAt = nextReviewAt;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getRepetition() {
        return repetition;
    }

    public void setRepetition(Integer repetition) {
        this.repetition = repetition;
    }

    public Double getEasiness() {
        return easiness;
    }

    public void setEasiness(Double easiness) {
        this.easiness = easiness;
    }

    public Integer getLastQuality() {
        return lastQuality;
    }

    public void setLastQuality(Integer lastQuality) {
        this.lastQuality = lastQuality;
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
