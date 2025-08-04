package br.com.study_smart_service.adapter.inbound.web.deck;

import br.com.study_smart_service.application.usecase.deck.CreateDeckUseCase;
import br.com.study_smart_service.domain.deck.dto.CreateDeckDto;
import br.com.study_smart_service.domain.deck.dto.DeckDto;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/deck")
public class CreateDeckController {

    private final CreateDeckUseCase createDeckService;

    @Tag(name = "Deck")
    @Operation(
            summary = "Criar novo deck vinculado ao usuário logado",
            description = "Cria novo deck e retorna os dados do deck criado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deck criado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeckDto.class)
                            )
                    )
            }
    )
    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeckDto> createDeck(
        @RequestBody CreateDeckDto createDeckRequestDto,
        Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
            createDeckService.execute(user.getId(), createDeckRequestDto.title())
        );
    }
}
