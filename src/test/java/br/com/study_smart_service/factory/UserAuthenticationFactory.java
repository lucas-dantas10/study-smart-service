package br.com.study_smart_service.factory;

import br.com.study_smart_service.domain.user.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public class UserAuthenticationFactory {

    public static Authentication userAuthentication(UUID userId) {
        User user = new User();
        user.setId(userId);

        return new UsernamePasswordAuthenticationToken(user, null, null);
    }

    public static Authentication userAuthentication() {
        return userAuthentication(UUID.randomUUID());
    }
}
