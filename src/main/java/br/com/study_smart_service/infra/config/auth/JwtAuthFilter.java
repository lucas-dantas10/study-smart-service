package br.com.study_smart_service.infra.config.auth;

import br.com.study_smart_service.domain.user.model.User;
import br.com.study_smart_service.domain.user.repository.UserRepository;
import br.com.study_smart_service.utils.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            throw new AuthorizationDeniedException("Não autorizado");
        }

        String token = authHeader.substring(7);

        Claims claims = jwtUtil.getClaims(token);

        User user = userRepository.findByEmail(claims.getSubject());

        if (user == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            throw new BadRequestException("Token inválido");
        }

        UsernamePasswordAuthenticationToken auth =  new UsernamePasswordAuthenticationToken(
                user, null, List.of());

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        return path.equals("/") ||
                path.startsWith("/auth/") ||
                path.startsWith("/oauth2/");
    }
}
