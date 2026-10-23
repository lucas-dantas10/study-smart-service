package br.com.study_smart_service.application.usecase.stats;

import br.com.study_smart_service.adapter.inbound.web.stats.dto.StatsDto;

import java.util.UUID;

public interface GetStatsUseCase {

    StatsDto execute(UUID userId);
}
