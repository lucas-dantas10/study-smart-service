package br.com.study_smart_service.application.usecase.user;

public interface UpdateUserUseCase {

    void execute(String id, String name, String email, String picture);
}
