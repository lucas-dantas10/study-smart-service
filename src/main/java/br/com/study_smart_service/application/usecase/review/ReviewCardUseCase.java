package br.com.study_smart_service.application.usecase.review;

import br.com.study_smart_service.domain.user.model.User;

import java.util.UUID;

public interface ReviewCardUseCase {

    public void execute(User user, UUID cardId, int quality) throws Exception;
}
