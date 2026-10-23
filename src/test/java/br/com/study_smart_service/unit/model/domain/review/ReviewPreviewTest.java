package br.com.study_smart_service.unit.model.domain.review;

import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.review.enums.EQuality;
import br.com.study_smart_service.domain.review.model.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReviewPreviewTest {

    @Test
    @DisplayName("A previsão deve coincidir com o intervalo da avaliação real")
    void previewShouldMatchRealReview() {
        for (EQuality quality : EQuality.values()) {
            Review review = new Review(null, null);
            int predicted = review.previewIntervalInDays(quality);

            review.review(quality);

            assertEquals(review.getInterval(), predicted, "qualidade " + quality);
        }
    }

    @Test
    @DisplayName("A previsão não deve alterar o estado da revisão")
    void previewShouldNotChangeState() {
        Review review = new Review(null, null);
        review.review(EQuality.EASY);

        int repetition = review.getRepetition();
        int interval = review.getInterval();
        double easiness = review.getEasiness();
        Integer lastQuality = review.getLastQuality();

        review.previewIntervalInDays(EQuality.HARD);
        review.previewIntervalInDays(EQuality.EASY);

        assertEquals(repetition, review.getRepetition());
        assertEquals(interval, review.getInterval());
        assertEquals(easiness, review.getEasiness());
        assertEquals(lastQuality, review.getLastQuality());
    }

    @Test
    @DisplayName("Avaliar como difícil sempre volta no dia seguinte")
    void hardShouldAlwaysComeBackTomorrow() {
        Review review = new Review(null, null);
        review.review(EQuality.EASY);
        review.review(EQuality.EASY);
        review.review(EQuality.EASY);

        assertEquals(1, review.previewIntervalInDays(EQuality.HARD));
    }

    @Test
    @DisplayName("Normaliza tags: minúsculas, sem espaços nem repetidas e com limite")
    void shouldNormalizeTags() {
        List<String> tags = Card.normalizeTags(List.of(" Anatomia ", "anatomia", "Prova Final", "c++!", "", "x".repeat(50)));

        assertEquals(List.of("anatomia", "prova-final", "c", "x".repeat(Card.MAX_TAG_LENGTH)), tags);
    }

    @Test
    @DisplayName("Limita a quantidade de tags por card")
    void shouldLimitTagCount() {
        List<String> many = List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10");

        assertEquals(Card.MAX_TAGS, Card.normalizeTags(many).size());
    }
}
