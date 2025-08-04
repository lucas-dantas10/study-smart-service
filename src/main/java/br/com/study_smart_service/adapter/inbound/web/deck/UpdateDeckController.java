package br.com.study_smart_service.adapter.inbound.web.deck;

import br.com.study_smart_service.adapter.inbound.web.deck.dto.DeckDto;
import br.com.study_smart_service.adapter.inbound.web.deck.dto.UpdateDeckDto;
import br.com.study_smart_service.application.usecase.deck.UpdateDeckUseCase;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/deck")
@RequiredArgsConstructor
public class UpdateDeckController {

    private final UpdateDeckUseCase updateDeckUseCase;

    @Tag(name = "Deck")
    @PutMapping("/{deckId}")
    public ResponseEntity<DeckDto> update(
            @RequestBody UpdateDeckDto dto,
            @PathVariable("deckId") String deck,
            Authentication authentication) throws Exception {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(updateDeckUseCase.execute(UUID.fromString(deck), user.getId(), dto.title()));
    }
}
