package br.com.study_smart_service.adapter.inbound.web.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardDto;
import br.com.study_smart_service.adapter.inbound.web.card.dto.CreateCardDto;
import br.com.study_smart_service.application.usecase.card.CreateCardUseCase;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/card")
@RequiredArgsConstructor
public class CreateCardController {

    private final CreateCardUseCase createCardUseCase;

    @Tag(name = "Card")
    @Operation(summary = "Cria card vinculado ao deck")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDto> create(
            @RequestBody CreateCardDto cardDto,
            Authentication authentication) throws Exception {
        User user = (User) authentication.getPrincipal();

        CardDto card = createCardUseCase.execute(
                UUID.fromString(cardDto.deckId()),
                user.getId(),
                cardDto.frontText(),
                cardDto.backText());

        return ResponseEntity.ok(card);
    }
}
