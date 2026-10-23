package br.com.study_smart_service.application.usecase.review;

import java.util.UUID;

public interface UndoReviewUseCase {

    void execute(UUID cardId, UUID userId) throws Exception;
}
