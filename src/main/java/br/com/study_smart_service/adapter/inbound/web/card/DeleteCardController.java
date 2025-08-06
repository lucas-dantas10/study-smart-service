package br.com.study_smart_service.adapter.inbound.web.card;

import br.com.study_smart_service.application.usecase.card.DeleteCardByIdAndUserIdUseCase;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/card")
@RequiredArgsConstructor
public class DeleteCardController {

    private final DeleteCardByIdAndUserIdUseCase deleteCardByIdAndUserIdUseCase;

    @Tag(name = "Card")
    @Operation(summary = "Deletar card pelo id do card")
    @DeleteMapping(value = "/{cardId}")
    public ResponseEntity<Void> delete(
            @PathVariable final String cardId,
            Authentication authentication) throws Exception {
        User user = (User) authentication.getPrincipal();

        deleteCardByIdAndUserIdUseCase.execute(UUID.fromString(cardId), user.getId());

        return ResponseEntity.ok().build();
    }
}
