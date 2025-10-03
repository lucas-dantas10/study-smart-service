package br.com.study_smart_service.application.usecase.user;

import br.com.study_smart_service.domain.user.model.User;

public interface FindUserByEmailUseCase {

    User execute(String email);
}
