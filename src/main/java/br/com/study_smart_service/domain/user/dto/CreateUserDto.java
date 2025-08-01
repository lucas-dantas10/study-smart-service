package br.com.study_smart_service.domain.user.dto;

public record CreateUserDto(
        String name,
        String email,
        String picture
) {
}
