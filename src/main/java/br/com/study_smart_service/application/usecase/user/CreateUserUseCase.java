package br.com.study_smart_service.application.usecase.user;

import br.com.study_smart_service.adapter.inbound.web.user.dto.CreateUserDto;
import br.com.study_smart_service.domain.user.model.User;

public interface CreateUserUseCase {

    User execute(CreateUserDto user);
}
