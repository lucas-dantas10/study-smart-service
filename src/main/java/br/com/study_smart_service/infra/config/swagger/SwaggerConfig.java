package br.com.study_smart_service.infra.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String OAUTH2_SCHEME_NAME = "oauth2scheme";
    private static final String BEARER_SCHEME_NAME = "bearerAuth";

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
                )
                .addSecurityItem(new SecurityRequirement().addList(OAUTH2_SCHEME_NAME))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(OAUTH2_SCHEME_NAME,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .flows(new OAuthFlows()
                                                .authorizationCode(new OAuthFlow()
                                                        .authorizationUrl("https://accounts.google.com/o/oauth2/v2/auth")
                                                        .tokenUrl("https://oauth2.googleapis.com/token")
                                                        .scopes(new Scopes()
                                                                .addString("openid", "Autenticação OpenID")
                                                                .addString("profile", "Acesso ao perfil do usuário")
                                                                .addString("email", "Acesso ao email do usuário")
                                                        )
                                                )
                                        )
                        )
                        .addSecuritySchemes(BEARER_SCHEME_NAME,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}
