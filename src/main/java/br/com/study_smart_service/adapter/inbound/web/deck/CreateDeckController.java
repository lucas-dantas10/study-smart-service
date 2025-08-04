package br.com.study_smart_service.adapter.inbound.web.deck;

import br.com.study_smart_service.application.usecase.deck.CreateDeckUseCase;
import br.com.study_smart_service.domain.deck.dto.CreateDeckDto;
import br.com.study_smart_service.domain.deck.dto.DeckDto;
import br.com.study_smart_service.domain.user.model.User;
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
