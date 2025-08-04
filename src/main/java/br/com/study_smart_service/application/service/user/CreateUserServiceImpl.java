package br.com.study_smart_service.application.service.user;

import br.com.study_smart_service.application.usecase.user.CreateUserUseCase;
import br.com.study_smart_service.adapter.inbound.web.user.dto.CreateUserDto;
import br.com.study_smart_service.domain.user.model.User;
import br.com.study_smart_service.domain.user.repository.UserRepository;
import br.com.study_smart_service.utils.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserServiceImpl implements CreateUserUseCase {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public User execute(CreateUserDto dto) {
        User user = userMapper.dtoToDomain(dto);

        userRepository.save(user);

        return user;
    }
}
