package br.com.study_smart_service.adapter.inbound.web.deck;

import br.com.study_smart_service.adapter.inbound.web.deck.dto.DeckDto;
import br.com.study_smart_service.adapter.inbound.web.deck.dto.UpdateDeckDto;
import br.com.study_smart_service.application.usecase.deck.UpdateDeckUseCase;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/deck")
@RequiredArgsConstructor
public class UpdateDeckController {

    private final UpdateDeckUseCase updateDeckUseCase;

    @Tag(name = "Deck")
    @Operation(summary = "Atualiza deck pelo id do deck e vinculado ao usuário logado")
    @PutMapping(
            value = "/{deckId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeckDto> update(
            @RequestBody UpdateDeckDto dto,
            @PathVariable("deckId") String deck,
            Authentication authentication) throws Exception {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(updateDeckUseCase.execute(UUID.fromString(deck), user.getId(), dto.title()));
    }
}
