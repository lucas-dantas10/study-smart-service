package br.com.study_smart_service.adapter.inbound.web.user.dto;

public record CreateUserDto(
        String name,
        String email,
        String picture
) {
}
