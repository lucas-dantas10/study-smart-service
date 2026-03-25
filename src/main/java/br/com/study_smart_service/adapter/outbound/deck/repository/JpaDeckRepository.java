package br.com.study_smart_service.adapter.outbound.deck.repository;

import br.com.study_smart_service.adapter.outbound.deck.entity.DeckEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaDeckRepository extends JpaRepository<DeckEntity, UUID> {

    @Query("SELECT d FROM DeckEntity d LEFT JOIN FETCH d.cards WHERE d.id = :deckId AND d.user.id = :userId")
    Optional<DeckEntity> findByIdAndUserIdWithCards(@Param("deckId") UUID deckId, @Param("userId") UUID userId);

    Page<DeckEntity> findAllByUserId(UUID userId, Pageable pageable);
}
