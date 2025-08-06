package br.com.study_smart_service.adapter.inbound.web.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardDto;
import br.com.study_smart_service.application.usecase.card.FindOneCardByIdAndUserIdUseCase;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value = "/api/v1/card")
@RequiredArgsConstructor
public class FindOneCardController {

    private final FindOneCardByIdAndUserIdUseCase findOneCardByIdAndUserIdUseCase;

    @Tag(name = "Card")
    @Operation(summary = "Busca um card pelo id do card e id do usuário logado")
    @GetMapping(value = "/{cardId}")
    public ResponseEntity<CardDto> findOne(
            @PathVariable final String cardId,
            Authentication authentication) throws Exception {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(findOneCardByIdAndUserIdUseCase.execute(UUID.fromString(cardId), user.getId()));
    }
}
