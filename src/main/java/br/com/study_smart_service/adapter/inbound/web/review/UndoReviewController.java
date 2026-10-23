package br.com.study_smart_service.adapter.inbound.web.review;

import br.com.study_smart_service.application.usecase.review.UndoReviewUseCase;
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
@RequestMapping(value = "/api/v1/review")
@RequiredArgsConstructor
public class UndoReviewController {

    private final UndoReviewUseCase undoReviewUseCase;

    @Tag(name = "Review")
    @Operation(summary = "Desfaz a última revisão do card")
    @DeleteMapping("/{cardId}/last")
    public ResponseEntity<Void> undo(
            @PathVariable String cardId,
            Authentication authentication) throws Exception {
        User user = (User) authentication.getPrincipal();

        undoReviewUseCase.execute(UUID.fromString(cardId), user.getId());

        return ResponseEntity.noContent().build();
    }
}
