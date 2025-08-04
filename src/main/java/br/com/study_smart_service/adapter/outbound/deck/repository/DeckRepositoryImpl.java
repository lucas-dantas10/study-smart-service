package br.com.study_smart_service.adapter.outbound.deck.repository;

import br.com.study_smart_service.adapter.outbound.deck.entity.DeckEntity;
import br.com.study_smart_service.domain.deck.model.Deck;
import br.com.study_smart_service.domain.deck.repository.DeckRepository;
import br.com.study_smart_service.utils.mapper.deck.DeckMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeckRepositoryImpl implements DeckRepository {

    private final JpaDeckRepository jpaDeckRepository;
    private final DeckMapper deckMapper;

    public Deck findById(UUID id) {
        return jpaDeckRepository.findById(id)
            .map(deckMapper::jpaToDomain)
            .orElse(null);
    }

    public Deck findByIdAndUserId(UUID id, UUID userId) {
        return jpaDeckRepository.findByIdAndUserId(id, userId)
                .map(deckMapper::jpaToDomain)
                .orElse(null);
    }

    public List<Deck> findAllByUserId(UUID userId) {
        return jpaDeckRepository.findAllByUserId(userId)
            .stream()
            .map(deckMapper::jpaToDomain)
            .toList();
    }

    public Deck save(Deck deck) {
        DeckEntity deckEntity = deckMapper.domainToJpa(deck);

        DeckEntity savedDeck = jpaDeckRepository.save(deckEntity);

        return deckMapper.jpaToDomain(savedDeck);
    }

    public void delete(Deck deck) {
        DeckEntity deckEntity = deckMapper.domainToJpa(deck);

        jpaDeckRepository.delete(deckEntity);
    }
}
