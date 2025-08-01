package br.com.study_smart_service.application.usecase.user;

import br.com.study_smart_service.domain.user.model.User;
import static org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface FindUserByEmailUseCase {

    User execute(String email);
}
