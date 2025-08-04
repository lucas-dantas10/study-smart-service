package br.com.study_smart_service.infra.config.auth;

import br.com.study_smart_service.application.usecase.user.CreateUserUseCase;
import br.com.study_smart_service.application.usecase.user.FindUserByEmailUseCase;
import br.com.study_smart_service.application.usecase.user.UpdateUserUseCase;
import br.com.study_smart_service.adapter.inbound.web.user.dto.CreateUserDto;
import br.com.study_smart_service.domain.user.model.User;
import br.com.study_smart_service.utils.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${jwt.frontend-redirect-url}")
    private String redirectUrl;

    private final JwtUtil jwtUtil;
    private final FindUserByEmailUseCase findUserByEmailUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = oauthToken.getPrincipal();

        String name = oauthUser.getAttribute("name");
        String email = oauthUser.getAttribute("email");
        String picture = oauthUser.getAttribute("picture");

        User user = findUserByEmailUseCase.execute(email);

        if (user == null) {
            createUserUseCase.execute(new CreateUserDto(name, email, picture));
        } else {
            updateUserUseCase.execute(user.getId().toString(), name, email, picture);
        }

        String jwt = jwtUtil.generateToken(name, email, picture);

        response.sendRedirect(redirectUrl + "?token=" + jwt);
    }
}
