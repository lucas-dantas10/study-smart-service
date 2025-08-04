package br.com.study_smart_service.adapter.inbound.web.user;

import br.com.study_smart_service.adapter.inbound.web.user.dto.UserResponseDto;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/me")
@Slf4j
public class MeController {

    @Tag(name = "User")
    @Operation(
        summary = "Buscar dados do usuário autenticado",
        description = "Retorna as informações básicas do usuário que está autenticado no sistema.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Dados do usuário retornados com sucesso",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Usuário não autenticado",
                content = @Content
            )
        }
    )
    @GetMapping
    public ResponseEntity<UserResponseDto> getMe(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new UserResponseDto(user.getId().toString(),
                user.getName(), user.getEmail(), user.getPicture()));
    }
}
