package br.com.study_smart_service.adapter.inbound.web.deck;

import br.com.study_smart_service.adapter.inbound.web.deck.dto.DeckDto;
import br.com.study_smart_service.application.usecase.deck.FindOneDeckByIdAndUserIdUseCase;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/deck")
@RequiredArgsConstructor
public class FindOneDeckController {

    private final FindOneDeckByIdAndUserIdUseCase findOneDeckByIdAndUserIdUseCase;

    @Tag(name = "Deck")
    @GetMapping(value = "/{deckId}")
    public ResponseEntity<DeckDto> findDeck(
            @PathVariable("deckId") String deckId,
            Authentication authentication) throws Exception {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(findOneDeckByIdAndUserIdUseCase.execute(UUID.fromString(deckId), user.getId()));

    }
}
