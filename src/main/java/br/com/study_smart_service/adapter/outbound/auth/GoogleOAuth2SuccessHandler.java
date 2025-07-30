package br.com.study_smart_service.adapter.outbound.auth;

import br.com.study_smart_service.utils.jwt.JwtUtil;
import jakarta.servlet.ServletException;
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

@Slf4j
@RequiredArgsConstructor
@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${jwt.frontend-redirect-url}")
    private String redirectUrl;

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = oauthToken.getPrincipal();

        String name = oauthUser.getAttribute("name");
        String email = oauthUser.getAttribute("email");
        String picture = oauthUser.getAttribute("picture");

        // TODO: createOrUpdate user

        String jwt = jwtUtil.generateToken(name, email);

        response.sendRedirect(redirectUrl + "?token=" + jwt);
    }
}
