package br.com.study_smart_service.integration.adapter.outbound.repository;

import br.com.study_smart_service.adapter.outbound.card.entity.CardEntity;
import br.com.study_smart_service.adapter.outbound.card.repository.JpaCardRepository;
import br.com.study_smart_service.adapter.outbound.deck.entity.DeckEntity;
import br.com.study_smart_service.adapter.outbound.review.entity.ReviewEntity;
import br.com.study_smart_service.adapter.outbound.review.repository.JpaReviewRepository;
import br.com.study_smart_service.adapter.outbound.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@DataJpaTest
class RetentionQueriesTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private JpaReviewRepository reviewRepository;

    @Autowired
    private JpaCardRepository cardRepository;

    private UserEntity user;
    private DeckEntity deck;

    @BeforeEach
    void setUp() {
        user = newUser("user@test.com");
        deck = newDeck(user, "Anatomia");
    }

    @Test
    @DisplayName("Deve contar as revisões por dia")
    void shouldCountReviewsByDay() throws Exception {
        CardEntity card = newCard(deck, "Femur", "Osso da coxa", null);
        newReview(card, LocalDate.now().plusDays(1), 3);
        newReview(card, LocalDate.now().plusDays(3), 2);

        List<Object[]> rows = reviewRepository.countReviewsByDay(user.getId());

        assertEquals(1, rows.size());
        assertEquals(2L, ((Number) rows.get(0)[1]).longValue());
        assertTrue(rows.get(0)[0].toString().startsWith(LocalDate.now().toString()));
    }

    @Test
    @DisplayName("Deve considerar só a última revisão de cada card na previsão")
    void shouldForecastUsingOnlyTheLatestReviewPerCard() throws Exception {
        CardEntity card = newCard(deck, "Femur", "Osso da coxa", null);
        CardEntity other = newCard(deck, "Tibia", "Osso da perna", null);
        newReview(card, LocalDate.now().minusDays(5), 1);
        newReview(card, LocalDate.now().plusDays(2), 3);
        newReview(other, LocalDate.now().minusDays(1), 1);

        List<Object[]> rows = reviewRepository.countDueByDate(user.getId(), LocalDate.now().plusDays(6));

        assertEquals(2, rows.size());
        long total = rows.stream().mapToLong(row -> ((Number) row[1]).longValue()).sum();
        assertEquals(2L, total);
    }

    @Test
    @DisplayName("Deve calcular acertos por deck considerando Médio e Fácil")
    void shouldCountCorrectAnswersByDeck() throws Exception {
        CardEntity card = newCard(deck, "Femur", "Osso da coxa", null);
        newReview(card, LocalDate.now().plusDays(1), 1);
        newReview(card, LocalDate.now().plusDays(1), 2);
        newReview(card, LocalDate.now().plusDays(1), 3);

        List<Object[]> rows = reviewRepository.accuracyByDeck(user.getId());

        assertEquals(1, rows.size());
        assertEquals("Anatomia", rows.get(0)[1]);
        assertEquals(3L, ((Number) rows.get(0)[2]).longValue());
        assertEquals(2L, ((Number) rows.get(0)[3]).longValue());
    }

    @Test
    @DisplayName("Não deve misturar estatísticas de usuários diferentes")
    void shouldIsolateStatsByUser() throws Exception {
        CardEntity card = newCard(deck, "Femur", "Osso da coxa", null);
        newReview(card, LocalDate.now().plusDays(1), 3);

        UserEntity stranger = newUser("stranger@test.com");

        assertTrue(reviewRepository.countReviewsByDay(stranger.getId()).isEmpty());
        assertTrue(reviewRepository.accuracyByDeck(stranger.getId()).isEmpty());
        assertEquals(0L, cardRepository.countWithoutReviewByUserId(stranger.getId()));
    }

    @Test
    @DisplayName("Deve devolver a última revisão dos cards informados")
    void shouldFindLatestReviewByCardIds() throws Exception {
        CardEntity card = newCard(deck, "Femur", "Osso da coxa", null);
        newReview(card, LocalDate.now().plusDays(1), 1);
        ReviewEntity latest = newReview(card, LocalDate.now().plusDays(9), 3);

        List<ReviewEntity> found = reviewRepository.findLatestByUserIdAndCardIds(user.getId(), List.of(card.getId()));

        assertEquals(1, found.size());
        assertEquals(latest.getId(), found.get(0).getId());
    }

    @Test
    @DisplayName("Deve contar apenas cards que nunca foram revisados")
    void shouldCountCardsWithoutReview() throws Exception {
        CardEntity reviewed = newCard(deck, "Femur", "Osso da coxa", null);
        newCard(deck, "Tibia", "Osso da perna", null);
        newCard(deck, "Fibula", "Osso fino da perna", null);
        newReview(reviewed, LocalDate.now().plusDays(1), 3);

        assertEquals(2L, cardRepository.countWithoutReviewByUserId(user.getId()));
    }

    @Test
    @DisplayName("Deve buscar por texto sem diferenciar maiúsculas e por tag")
    void shouldSearchByTextAndTag() throws Exception {
        newCard(deck, "Femur", "Osso da coxa", "anatomia,prova");
        newCard(deck, "Mitocondria", "Usina da celula", "biologia");

        var page = PageRequest.of(0, 50);

        assertEquals(1, cardRepository.search(user.getId(), "%femur%", "", page).size());
        assertEquals(1, cardRepository.search(user.getId(), "%usina%", "", page).size());
        assertEquals(1, cardRepository.search(user.getId(), "", "%,prova,%", page).size());
        assertEquals(0, cardRepository.search(user.getId(), "", "%,prov,%", page).size());
        assertEquals(2, cardRepository.search(user.getId(), "", "", page).size());
    }

    @Test
    @DisplayName("Deve tratar curingas do LIKE como texto literal")
    void shouldTreatLikeWildcardsAsLiteralText() throws Exception {
        newCard(deck, "100% certo", "Verso", null);
        newCard(deck, "Outro card", "Verso", null);

        var page = PageRequest.of(0, 50);

        assertEquals(1, cardRepository.search(user.getId(), "%100!%%", "", page).size());
    }

    @Test
    @DisplayName("Deve limitar a busca ao deck e ao usuário informados")
    void shouldLimitSearchToDeckAndUser() throws Exception {
        DeckEntity otherDeck = newDeck(user, "Biologia");
        newCard(deck, "Femur", "Osso da coxa", null);
        newCard(otherDeck, "Femur de rato", "Osso", null);

        UserEntity stranger = newUser("stranger@test.com");
        DeckEntity strangerDeck = newDeck(stranger, "Dele");
        newCard(strangerDeck, "Femur alheio", "Osso", null);

        var page = PageRequest.of(0, 50);

        assertEquals(2, cardRepository.search(user.getId(), "%femur%", "", page).size());
        assertEquals(1, cardRepository.searchInDeck(user.getId(), deck.getId(), "%femur%", "", page).size());
        assertEquals(0, cardRepository.searchInDeck(user.getId(), strangerDeck.getId(), "%femur%", "", page).size());
    }

    private UserEntity newUser(String email) {
        UserEntity entity = new UserEntity();
        entity.setName("Tester");
        entity.setEmail(email);
        entity.setPicture("http://picture");
        entity.setCreatedAt(LocalDateTime.now());

        return em.persistAndFlush(entity);
    }

    private DeckEntity newDeck(UserEntity owner, String title) {
        DeckEntity entity = new DeckEntity();
        entity.setUser(owner);
        entity.setTitle(title);
        entity.setCreatedAt(LocalDateTime.now());

        return em.persistAndFlush(entity);
    }

    private CardEntity newCard(DeckEntity owner, String front, String back, String tags) {
        CardEntity entity = new CardEntity();
        entity.setDeck(owner);
        entity.setFrontText(front);
        entity.setBackText(back);
        entity.setTags(tags);
        entity.setCreatedAt(LocalDateTime.now());

        return em.persistAndFlush(entity);
    }

    private ReviewEntity newReview(CardEntity card, LocalDate nextReviewAt, int quality) throws Exception {
        ReviewEntity entity = new ReviewEntity();
        entity.setUser(user);
        entity.setCard(card);
        entity.setNextReviewAt(nextReviewAt);
        entity.setInterval(1);
        entity.setRepetition(1);
        entity.setEasiness(2.5);
        entity.setLastQuality(quality);

        ReviewEntity saved = em.persistAndFlush(entity);

        // created_at é preenchido pelo banco; separa as revisões para a ordenação "última" ser determinística
        Thread.sleep(10);

        return saved;
    }
}
