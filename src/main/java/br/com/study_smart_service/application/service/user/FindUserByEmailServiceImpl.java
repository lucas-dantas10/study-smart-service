package br.com.study_smart_service.application.service.user;

import br.com.study_smart_service.application.usecase.user.FindUserByEmailUseCase;
import br.com.study_smart_service.domain.user.model.User;
import br.com.study_smart_service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserByEmailServiceImpl implements FindUserByEmailUseCase {

    private final UserRepository userRepository;

    public User execute(String email) {
        return userRepository.findByEmail(email);
    }
}
