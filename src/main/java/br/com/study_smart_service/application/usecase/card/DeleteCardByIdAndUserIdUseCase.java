package br.com.study_smart_service.application.usecase.card;

import java.util.UUID;

public interface DeleteCardByIdAndUserIdUseCase {

    void execute(UUID cardId, UUID userId) throws Exception;
}
