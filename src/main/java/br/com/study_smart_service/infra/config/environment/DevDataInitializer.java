package br.com.study_smart_service.infra.config.environment;


import br.com.study_smart_service.adapter.inbound.web.user.dto.CreateUserDto;
import br.com.study_smart_service.application.usecase.deck.CreateDeckUseCase;
import br.com.study_smart_service.application.usecase.user.CreateUserUseCase;
import br.com.study_smart_service.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!prod && !test")
@RequiredArgsConstructor
public class DevDataInitializer {

    private final CreateDeckUseCase createDeckUseCase;
    private final CreateUserUseCase createUserUseCase;

    @Value("${user.email}")
    private String emailTeste;

    @Bean
    CommandLineRunner init() {
        return args -> {
            int totalDecks = 20;

            CreateUserDto userDto = new CreateUserDto(
                    "Lucas Dantas",
                    emailTeste,
                    "teste");
            User user = createUserUseCase.execute(userDto);

            for (int i = 0; i < totalDecks; i++) {
                createDeckUseCase.execute(user.getId(), "teste " + i);
            }
        };
    }
}
