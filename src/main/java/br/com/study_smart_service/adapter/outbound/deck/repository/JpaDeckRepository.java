package br.com.study_smart_service.adapter.outbound.deck.repository;

import br.com.study_smart_service.adapter.outbound.deck.entity.DeckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaDeckRepository extends JpaRepository<DeckEntity, UUID> {

    List<DeckEntity> findAllByUserId(UUID userId);
}
