package br.com.study_smart_service.domain.user.dto;

public record UserResponseDto(
        String id,
        String name,
        String email,
        String picture
) {
}
