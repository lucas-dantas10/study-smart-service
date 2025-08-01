package br.com.study_smart_service.adapter.inbound.web.user;

import br.com.study_smart_service.domain.user.dto.UserResponseDto;
import br.com.study_smart_service.domain.user.model.User;
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

    @GetMapping
    public ResponseEntity<?> getMe(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new UserResponseDto(user.getId().toString(),
                user.getName(), user.getEmail(), user.getPicture()));
    }
}
