package br.com.study_smart_service.utils.mapper.card;

import br.com.study_smart_service.adapter.outbound.card.entity.CardEntity;
import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.utils.mapper.deck.DeckMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = DeckMapper.class)
public interface CardMapper {

    @Mappings({
            @Mapping(source = "cardJpa.id", target = "id"),
            @Mapping(source = "cardJpa.deck", target = "deck"),
            @Mapping(source = "cardJpa.frontText", target = "frontText"),
            @Mapping(source = "cardJpa.backText", target = "backText"),
            @Mapping(source = "cardJpa.createdAt", target = "createdAt"),
            @Mapping(source = "cardJpa.updatedAt", target = "updatedAt")
    })
    Card jpaToDomain(CardEntity cardJpa);

    @Mappings({
            @Mapping(source = "card.id", target = "id"),
            @Mapping(source = "card.deck", target = "deck"),
            @Mapping(source = "card.frontText", target = "frontText"),
            @Mapping(source = "card.backText", target = "backText"),
            @Mapping(source = "card.createdAt", target = "createdAt"),
            @Mapping(source = "card.updatedAt", target = "updatedAt")
    })
    CardEntity domainToJpa(Card card);
}
