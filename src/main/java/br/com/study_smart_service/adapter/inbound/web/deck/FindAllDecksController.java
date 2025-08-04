package br.com.study_smart_service.adapter.inbound.web.deck;

import br.com.study_smart_service.adapter.inbound.web.deck.dto.DeckDto;
import br.com.study_smart_service.application.usecase.deck.FindAllDeckByUserIdUseCase;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/deck")
@RequiredArgsConstructor
public class FindAllDecksController {

    private final FindAllDeckByUserIdUseCase findAllDeckByUserIdUseCase;

    @Tag(name = "Deck")
    @Operation(summary = "Busca todos os decks vinculados ao usuário logado")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DeckDto>> findAll(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(findAllDeckByUserIdUseCase.execute(user.getId()));
    }
}
