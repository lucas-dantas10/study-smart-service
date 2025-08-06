package br.com.study_smart_service.infra.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("Documentação do Study Service API")
                    .version("1.0.0")
                        .description("API para gerenciamento de decks, cards e usuários")
                        .contact(new Contact()
                            .name("Lucas Dantas")
                            .email("lucas.dantas.nogueira@gmail.com")
                            .url("https://lucasdantas.netlify.app/")
                        )
                        .license(new License()
                            .name("Apache 2.0")
                            .url("https://www.apache.org/licenses/LICENSE-2.0")
                        )
                );
    }
}
