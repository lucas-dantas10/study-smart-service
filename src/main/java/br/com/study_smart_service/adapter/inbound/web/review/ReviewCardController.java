package br.com.study_smart_service.adapter.inbound.web.review;

import br.com.study_smart_service.adapter.inbound.web.review.dto.ReviewCardDto;
import br.com.study_smart_service.application.usecase.review.ReviewCardUseCase;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/review")
@RequiredArgsConstructor
public class ReviewCardController {

    private final ReviewCardUseCase reviewCardUseCase;

    @Tag(name = "Review")
    @Operation(summary = "Cria revisão do card no banco")
    @PostMapping(value = "/{cardId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> reviewCard(
            @PathVariable("cardId") String cardId,
            @RequestBody ReviewCardDto reviewCardDto,
            Authentication authentication) throws Exception {
        User user = (User) authentication.getPrincipal();

        reviewCardUseCase.execute(user, UUID.fromString(cardId), reviewCardDto.quality());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
