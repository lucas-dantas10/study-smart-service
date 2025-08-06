package br.com.study_smart_service.utils.mapper.deck;

import br.com.study_smart_service.adapter.outbound.deck.entity.DeckEntity;
import br.com.study_smart_service.domain.deck.model.Deck;
import br.com.study_smart_service.utils.mapper.card.CardMapper;
import br.com.study_smart_service.utils.mapper.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface DeckMapper {

    @Mappings({
        @Mapping(source = "deckJpa.id", target = "id"),
        @Mapping(source = "deckJpa.user", target = "user"),
        @Mapping(source = "deckJpa.title", target = "title"),
        @Mapping(source = "deckJpa.cards", target = "cards", ignore = true),
        @Mapping(source = "deckJpa.createdAt", target = "createdAt"),
        @Mapping(source = "deckJpa.updatedAt", target = "updatedAt")
    })
    Deck jpaToDomain(DeckEntity deckJpa);

    @Mappings({
        @Mapping(source = "deck.id", target = "id"),
        @Mapping(source = "deck.user", target = "user"),
        @Mapping(source = "deck.title", target = "title"),
        @Mapping(source = "deck.cards", target = "cards", ignore = true),
        @Mapping(source = "deck.createdAt", target = "createdAt"),
        @Mapping(source = "deck.updatedAt", target = "updatedAt")
    })
    DeckEntity domainToJpa(Deck deck);
}
