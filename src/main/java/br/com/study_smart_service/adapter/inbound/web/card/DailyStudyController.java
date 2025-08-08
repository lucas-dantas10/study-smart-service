package br.com.study_smart_service.adapter.inbound.web.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardStudyDto;
import br.com.study_smart_service.application.usecase.card.DailyStudyUseCase;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/card")
@RequiredArgsConstructor
public class DailyStudyController {

    private final DailyStudyUseCase dailyStudyUseCase;

    @Tag(name = "Card")
    @Operation(summary = "Busca os cards de estudo considerando a data atual")
    @GetMapping("/{deckId}/study")
    public ResponseEntity<List<CardStudyDto>> listReviewCards(
            @PathVariable("deckId") String deckId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(dailyStudyUseCase.execute(UUID.fromString(deckId), user.getId()));
    }
}
