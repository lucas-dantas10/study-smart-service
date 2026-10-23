package br.com.study_smart_service.adapter.inbound.web.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardSearchDto;
import br.com.study_smart_service.application.usecase.card.SearchCardsUseCase;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/card")
@RequiredArgsConstructor
public class SearchCardsController {

    private final SearchCardsUseCase searchCardsUseCase;

    @Tag(name = "Card")
    @Operation(summary = "Busca cards do usuário por texto e/ou tag, em todos os decks ou em um deck")
    @GetMapping("/search")
    public ResponseEntity<List<CardSearchDto>> search(
            @RequestParam(name = "q", required = false) String text,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "deck_id", required = false) String deckId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        UUID deck = deckId == null || deckId.isBlank() ? null : UUID.fromString(deckId);

        return ResponseEntity.ok(searchCardsUseCase.execute(user.getId(), deck, text, tag));
    }
}
