package br.com.study_smart_service.infra.config.ratelimit;

import io.github.bucket4j.Bucket;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RateLimitFilter implements Filter {

    @Value("${rate-limit.minutes}")
    private int minutes;

    @Value("${rate-limit.tokens}")
    private int tokens;

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {
        String ip = request.getRemoteAddr();

        Bucket bucket = buckets.computeIfAbsent(ip, this::newBucket);

        if (!bucket.tryConsume(1)) {
            log.error("Muitas solicitações: {}", bucket.getAvailableTokens());
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.getWriter().write("Muitas solicitações");

            return;
        }

        filterChain.doFilter(request, response);
    }

    private Bucket newBucket(final String key) {
        return Bucket.builder()
                .addLimit(limit
                        -> limit.capacity(tokens).refillGreedy(tokens, Duration.ofMinutes(minutes)))
                .build();
    }
}
