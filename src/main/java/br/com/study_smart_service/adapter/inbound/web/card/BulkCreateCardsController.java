package br.com.study_smart_service.adapter.inbound.web.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.BulkCreateCardsDto;
import br.com.study_smart_service.adapter.inbound.web.card.dto.BulkCreateCardsResponseDto;
import br.com.study_smart_service.application.usecase.card.BulkCreateCardsUseCase;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/card")
@RequiredArgsConstructor
public class BulkCreateCardsController {

    private final BulkCreateCardsUseCase bulkCreateCardsUseCase;

    @Tag(name = "Card")
    @Operation(summary = "Cria vários cards de uma vez no mesmo deck (até 500)")
    @PostMapping(value = "/bulk", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BulkCreateCardsResponseDto> bulkCreate(
            @Valid @RequestBody BulkCreateCardsDto request,
            Authentication authentication) throws Exception {
        User user = (User) authentication.getPrincipal();

        int created = bulkCreateCardsUseCase.execute(user.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BulkCreateCardsResponseDto(created));
    }
}
