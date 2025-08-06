package br.com.study_smart_service.adapter.inbound.web.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardDto;
import br.com.study_smart_service.application.usecase.card.UpdateCardUseCase;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/card")
@RequiredArgsConstructor
public class UpdateCardController {

    private final UpdateCardUseCase updateCardUseCase;

    @Tag(name = "Card")
    @Operation(summary = "Atualiza card pelo id do card")
    @PutMapping(value = "/{cardId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDto> update(
            @PathVariable("cardId") String cardId,
            @RequestBody CardDto cardDto,
            Authentication authentication
    ) throws Exception {
        User user = (User) authentication.getPrincipal();

        CardDto card = updateCardUseCase.execute(
                UUID.fromString(cardId),
                user.getId(),
                cardDto.frontText(),
                cardDto.backText());

        return ResponseEntity.ok(card);
    }
}
