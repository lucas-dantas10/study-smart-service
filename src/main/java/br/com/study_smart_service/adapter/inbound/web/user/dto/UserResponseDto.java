package br.com.study_smart_service.adapter.inbound.web.user.dto;

public record UserResponseDto(
        String id,
        String name,
        String email,
        String picture
) {
}
