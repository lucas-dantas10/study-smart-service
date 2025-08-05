package br.com.study_smart_service.adapter.inbound.web.deck;

import br.com.study_smart_service.application.usecase.deck.DeleteDeckUseCase;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/deck")
@RequiredArgsConstructor
public class DeleteDeckController {

    private final DeleteDeckUseCase deleteDeckUseCase;

    @Tag(name = "Deck")
    @Operation(summary = "Deleta deck pelo id do deck e vinculado ao usuário logado")
    @DeleteMapping(value = "/{deckId}")
    public ResponseEntity<Void> delete(
            @PathVariable("deckId") String deckId,
            Authentication authentication) throws Exception {
        User user = (User) authentication.getPrincipal();

        deleteDeckUseCase.execute(UUID.fromString(deckId), user.getId());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
