package br.com.study_smart_service.adapter.inbound.web.stats;

import br.com.study_smart_service.adapter.inbound.web.stats.dto.StatsDto;
import br.com.study_smart_service.application.usecase.stats.GetStatsUseCase;
import br.com.study_smart_service.domain.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/stats")
@RequiredArgsConstructor
public class StatsController {

    private final GetStatsUseCase getStatsUseCase;

    @Tag(name = "Stats")
    @Operation(summary = "Estatísticas de estudo: sequência, atividade, previsão e acerto por deck")
    @GetMapping
    public ResponseEntity<StatsDto> stats(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(getStatsUseCase.execute(user.getId()));
    }
}
