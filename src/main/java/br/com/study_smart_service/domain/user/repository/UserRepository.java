package br.com.study_smart_service.domain.user.repository;

import br.com.study_smart_service.domain.user.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    void save(User user);
    User findByEmail(String email);
    User findByName(String username);
    User findById(UUID id);
    List<User> findAll();
}
