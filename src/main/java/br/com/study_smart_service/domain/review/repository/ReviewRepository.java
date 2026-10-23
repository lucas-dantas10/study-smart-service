package br.com.study_smart_service.domain.review.repository;

import br.com.study_smart_service.domain.review.model.DailyReviewCount;
import br.com.study_smart_service.domain.review.model.DeckAccuracy;
import br.com.study_smart_service.domain.review.model.Review;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ReviewRepository {

    Review findFirstByCardIdAndUserId(UUID id, UUID userId);
    Review save(Review review);
    void deleteByCardId(UUID cardId);
    Review findRecentByCardId(UUID cardId);

    /** Remove a revisão mais recente do card; devolve false quando o card nunca foi revisado. */
    boolean deleteLatestByCardIdAndUserId(UUID cardId, UUID userId);

    /** Última revisão de cada card informado, indexada pelo id do card. */
    Map<UUID, Review> findLatestByUserIdAndCardIds(UUID userId, Collection<UUID> cardIds);

    /** Quantidade de revisões feitas em cada dia, em todo o histórico do usuário. */
    List<DailyReviewCount> countReviewsByDay(UUID userId);

    /** Cards cuja última revisão os agenda até a data informada (atrasados inclusos), por data agendada. */
    List<DailyReviewCount> countDueByDate(UUID userId, LocalDate until);

    List<DeckAccuracy> accuracyByDeck(UUID userId);
}
