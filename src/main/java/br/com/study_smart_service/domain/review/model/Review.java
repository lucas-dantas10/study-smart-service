package br.com.study_smart_service.domain.review.model;

import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.review.enums.EQuality;
import br.com.study_smart_service.domain.user.model.User;

import java.time.LocalDate;
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

    public Review() {
        this.repetition = 0;
        this.interval = 1;
        this.easiness = 2.5;
        this.nextReviewAt = LocalDate.now();
    }

    public void review(EQuality quality) {
        int qualityValue = quality.getQuality();

        if (quality.isDifficult()) {
            repetition = 0;
            interval = 1;

            nextReviewAt = LocalDate.now().plusDays((long) interval);

            return;
        }

        repetition++;

        if (repetition == 1) {
            interval = 1;
        } else if (repetition == 2) {
            interval = 3;
        } else {
            interval = Math.toIntExact(Math.round(interval * easiness));
        }

        easiness += formulaEasiness(qualityValue);

        if (easiness < LOWER_LIMIT) {
            easiness = LOWER_LIMIT;
        }

        nextReviewAt = LocalDate.now().plusDays((long) interval);
    }

    private Double formulaEasiness(int lastQuality) {
        double qualityReward = REWARD_PER_QUALITY * lastQuality;
        double qualityPenalty = QUADRATIC_PENALTY * lastQuality * lastQuality;

        return -PENALTY_BASE + qualityReward - qualityPenalty;
    }
}
