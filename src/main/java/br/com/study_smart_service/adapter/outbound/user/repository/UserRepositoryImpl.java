package br.com.study_smart_service.adapter.outbound.user.repository;

import br.com.study_smart_service.adapter.outbound.user.entity.UserEntity;
import br.com.study_smart_service.domain.user.model.User;
import br.com.study_smart_service.domain.user.repository.UserRepository;
import br.com.study_smart_service.utils.mapper.user.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository, UserMapper userMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        UserEntity userEntity = userMapper.domainToJpa(user);

        this.jpaUserRepository.save(userEntity);
    }

    @Override
    public User findByEmail(String email) {
        return this.jpaUserRepository.findByEmail(email)
                .map(userMapper::jpaToDomain)
                .orElse(null);
    }

    @Override
    public User findByName(String name) {
        return this.jpaUserRepository.findByName(name)
                .map(userMapper::jpaToDomain)
                .orElse(null);
    }

    @Override
    public User findById(UUID id) {
        return this.jpaUserRepository.findById(id)
                .map(userMapper::jpaToDomain)
                .orElse(null);
    }

    @Override
    public List<User> findAll() {
        return this.jpaUserRepository.findAll()
                .stream()
                .map(userMapper::jpaToDomain)
                .toList();
    }
}
