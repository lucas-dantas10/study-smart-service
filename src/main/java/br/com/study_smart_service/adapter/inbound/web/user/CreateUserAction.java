package br.com.study_smart_service.adapter.inbound.web.user;

import br.com.study_smart_service.application.usecase.user.CreateUserUseCase;
import br.com.study_smart_service.domain.user.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class CreateUserAction {

    private final CreateUserUseCase createUserUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto dto) {
        createUserUseCase.execute(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
