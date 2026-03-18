package br.com.study_smart_service.unit.model.domain.review;

import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.review.enums.EQuality;
import br.com.study_smart_service.domain.review.model.Review;
import br.com.study_smart_service.domain.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class ReviewTest {

    private Review review;
    private User user;
    private Card card;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
        card = mock(Card.class);
        review = new Review(user, card);
    }

    @Test
    @DisplayName("Deve criar Review com valores iniciais corretos")
    void shouldCreateReviewWithDefaultValues() {
        assertEquals(0, review.getRepetition());
        assertEquals(1, review.getInterval());
        assertEquals(2.5, review.getEasiness());
        assertEquals(LocalDate.now(), review.getNextReviewAt());
    }

    @Test
    @DisplayName("Quando qualidade for difícil, deve resetar repetição e intervalo")
    void shouldResetRepetitionAndIntervalWhenQualityIsDifficult() {
        review.review(EQuality.HARD);

        assertEquals(0, review.getRepetition());
        assertEquals(1, review.getInterval());
        assertEquals(EQuality.HARD.getQuality(), review.getLastQuality());
        assertEquals(LocalDate.now().plusDays(1), review.getNextReviewAt());
    }

    @Test
    @DisplayName("Primeira repetição deve gerar intervalo de 1 dia")
    void shouldSetIntervalOneOnFirstRepetition() {
        review.review(EQuality.EASY);

        assertEquals(1, review.getRepetition());
        assertEquals(1, review.getInterval());
        assertEquals(LocalDate.now().plusDays(1), review.getNextReviewAt());
    }

    @Test
    @DisplayName("Segunda repetição deve gerar intervalo de 3 dias")
    void shouldSetIntervalThreeOnSecondRepetition() {
        review.review(EQuality.EASY);
        review.review(EQuality.EASY);

        assertEquals(2, review.getRepetition());
        assertEquals(3, review.getInterval());
        assertEquals(LocalDate.now().plusDays(3), review.getNextReviewAt());
    }

    @Test
    @DisplayName("A partir da terceira repetição deve usar intervalo * easiness")
    void shouldCalculateIntervalUsingEasinessAfterSecondRepetition() {
        review.review(EQuality.EASY);
        review.review(EQuality.EASY);
        review.review(EQuality.EASY);

        assertEquals(3, review.getRepetition());
        assertTrue(review.getInterval() >= 3);
        assertEquals(
                LocalDate.now().plusDays(review.getInterval()),
                review.getNextReviewAt()
        );
    }

    @Test
    @DisplayName("Easiness nunca deve ficar abaixo do limite mínimo")
    void shouldNotAllowEasinessBelowLowerLimit() {
        for (int i = 0; i < 10; i++) {
            review.review(EQuality.HARD);
        }

        assertTrue(review.getEasiness() >= 1.3);
    }
}