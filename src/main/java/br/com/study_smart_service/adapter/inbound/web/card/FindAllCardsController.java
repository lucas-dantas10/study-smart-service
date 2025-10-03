package br.com.study_smart_service.adapter.inbound.web.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.FindCardDto;
import br.com.study_smart_service.application.usecase.card.FindAllCardsByDeckIdUseCase;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/card")
@RequiredArgsConstructor
public class FindAllCardsController {

    private final FindAllCardsByDeckIdUseCase findAllCardsByDeckIdUseCase;

    @Tag(name = "Card")
    @Operation(summary = "Busca todos os cards vinculados a um deck")
    @GetMapping(value = "/{deckId}/deck", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FindCardDto>> findAll(
            @PathVariable final String deckId,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                findAllCardsByDeckIdUseCase.execute(
                        UUID.fromString(deckId),
                        user.getId())
        );

    }
}
