package br.com.study_smart_service.adapter.outbound.card.repository;

import br.com.study_smart_service.adapter.outbound.card.entity.CardEntity;
import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import br.com.study_smart_service.utils.mapper.card.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CardRepositoryImpl implements CardRepository {

    private final JpaCardRepository jpaCardRepository;
    private final CardMapper cardMapper;

    public Card findById(UUID id) {
        return jpaCardRepository.findById(id)
                .map(cardMapper::jpaToDomain)
                .orElse(null);
    }

    @Override
    public Card findByIdAndUserId(UUID id, UUID userId) {
        CardEntity cardEntity = jpaCardRepository.findByIdAndUserId(id, userId);

        return cardMapper.jpaToDomain(cardEntity);
    }

    public List<Card> findAllByDeckIdAndUserId(UUID deckId, UUID userId) {
        return jpaCardRepository.findAllByDeckIdAndUserId(deckId, userId)
                .stream()
                .map(cardMapper::jpaToDomain)
                .toList();
    }

    @Override
    public List<Card> findAllStudyCardsByDeckIdAndUserId(UUID deckId, UUID userId) {
        return jpaCardRepository.findAllStudyCardsByDeckIdAndUserId(deckId, userId)
                .stream()
                .map(cardMapper::jpaToDomain)
                .toList();
    }

    @Override
    public List<Card> findAllStudyCardsByUserId(UUID userId) {
        return jpaCardRepository.findAllStudyCardsByUserId(userId)
                .stream()
                .map(cardMapper::jpaToDomain)
                .toList();
    }

    public Card save(Card card) {
        CardEntity cardEntity = cardMapper.domainToJpaWithDeck(card);

        cardEntity = jpaCardRepository.save(cardEntity);

        return cardMapper.jpaToDomain(cardEntity);
    }

    public void delete(Card card) {
        CardEntity cardEntity = cardMapper.domainToJpa(card);

        jpaCardRepository.delete(cardEntity);
    }
}
