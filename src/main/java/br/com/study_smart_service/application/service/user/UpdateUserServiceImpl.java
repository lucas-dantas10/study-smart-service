package br.com.study_smart_service.application.service.user;

import br.com.study_smart_service.application.usecase.user.UpdateUserUseCase;
import br.com.study_smart_service.domain.user.model.User;
import br.com.study_smart_service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserServiceImpl implements UpdateUserUseCase {

    private final UserRepository userRepository;

    public void execute(String id, String name, String email, String picture) {
        User user = userRepository.findById(UUID.fromString(id));

        user.update(name, email, picture);

        userRepository.save(user);
    }
}
