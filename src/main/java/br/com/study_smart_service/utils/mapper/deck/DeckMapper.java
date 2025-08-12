package br.com.study_smart_service.utils.mapper.deck;

import br.com.study_smart_service.adapter.outbound.card.entity.CardEntity;
import br.com.study_smart_service.adapter.outbound.deck.entity.DeckEntity;
import br.com.study_smart_service.adapter.outbound.user.entity.UserEntity;
import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.deck.model.Deck;
import br.com.study_smart_service.domain.user.model.User;
import br.com.study_smart_service.utils.mapper.user.UserMapper;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface DeckMapper {

    default Deck jpaToDomain(DeckEntity deckEntity) {
        List<Card> cards = new ArrayList<>();
        UserEntity userEntity = deckEntity.getUser();

        User user = new User();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setEmail(userEntity.getEmail());
        user.setPicture(userEntity.getPicture());
        user.setCreatedAt(userEntity.getCreatedAt());
        user.setUpdatedAt(userEntity.getUpdatedAt());

        if (deckEntity.getCards() != null) {
            cards = deckEntity.getCards().stream().map(
                    cardEntity -> new Card(cardEntity.getId(),
                            cardEntity.getFrontText(),
                            cardEntity.getBackText(),
                            cardEntity.getCreatedAt(),
                            cardEntity.getUpdatedAt())
            ).toList();
        }

        Deck deck = new Deck();
        deck.setCards(cards);
        deck.setId(deckEntity.getId());
        deck.setTitle(deckEntity.getTitle());
        deck.setUser(user);
        deck.setCreatedAt(deckEntity.getCreatedAt());
        deck.setUpdatedAt(deckEntity.getUpdatedAt());

        return deck;
    }

    default DeckEntity domainToJpa(Deck deck) {
        List<CardEntity> cardsEntity = new ArrayList<>();
        User user = deck.getUser();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPicture(user.getPicture());
        userEntity.setCreatedAt(user.getCreatedAt());
        userEntity.setUpdatedAt(user.getUpdatedAt());

        if (deck.getCards() != null) {
            cardsEntity = deck.getCards().stream().map(
                    card -> new CardEntity(card.getId(),
                            card.getFrontText(),
                            card.getBackText(),
                            card.getCreatedAt(),
                            card.getUpdatedAt())
            ).toList();
        }

        DeckEntity deckEntity = new DeckEntity();
        deckEntity.setId(deck.getId());
        deckEntity.setTitle(deck.getTitle());
        deckEntity.setCards(cardsEntity);
        deckEntity.setUser(userEntity);
        deckEntity.setCreatedAt(deck.getCreatedAt());
        deckEntity.setUpdatedAt(deck.getUpdatedAt());

        return deckEntity;
    };
}
