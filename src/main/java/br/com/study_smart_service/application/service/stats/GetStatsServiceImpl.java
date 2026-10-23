package br.com.study_smart_service.application.service.stats;

import br.com.study_smart_service.adapter.inbound.web.stats.dto.StatsDto;
import br.com.study_smart_service.adapter.inbound.web.stats.dto.StatsDto.DayCountDto;
import br.com.study_smart_service.adapter.inbound.web.stats.dto.StatsDto.DeckAccuracyDto;
import br.com.study_smart_service.application.usecase.stats.GetStatsUseCase;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import br.com.study_smart_service.domain.review.model.DailyReviewCount;
import br.com.study_smart_service.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetStatsServiceImpl implements GetStatsUseCase {

    static final int ACTIVITY_DAYS = 182;
    static final int FORECAST_DAYS = 7;

    private final ReviewRepository reviewRepository;
    private final CardRepository cardRepository;

    public StatsDto execute(UUID userId) {
        LocalDate today = LocalDate.now();

        Map<LocalDate, Long> reviewsByDay = new HashMap<>();
        for (DailyReviewCount row : reviewRepository.countReviewsByDay(userId)) {
            reviewsByDay.merge(row.date(), row.count(), Long::sum);
        }

        LocalDate activityStart = today.minusDays(ACTIVITY_DAYS - 1L);
        List<DayCountDto> activity = reviewsByDay.entrySet().stream()
                .filter(entry -> !entry.getKey().isBefore(activityStart))
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new DayCountDto(entry.getKey().toString(), entry.getValue()))
                .toList();

        return new StatsDto(
                StreakCalculator.current(reviewsByDay.keySet(), today),
                StreakCalculator.longest(reviewsByDay.keySet()),
                reviewsByDay.values().stream().mapToLong(Long::longValue).sum(),
                reviewsByDay.getOrDefault(today, 0L),
                activity,
                buildForecast(userId, today),
                reviewRepository.accuracyByDeck(userId).stream()
                        .map(row -> new DeckAccuracyDto(
                                row.deckId().toString(), row.deckTitle(), row.reviews(), row.correct()))
                        .toList()
        );
    }

    /**
     * Cards agendados para cada um dos próximos dias. Os atrasados e os cards novos
     * (nunca revisados) entram em hoje, porque é quando aparecem na fila de estudo.
     */
    private List<DayCountDto> buildForecast(UUID userId, LocalDate today) {
        Map<LocalDate, Long> dueByDay = new HashMap<>();

        for (DailyReviewCount row : reviewRepository.countDueByDate(userId, today.plusDays(FORECAST_DAYS - 1L))) {
            LocalDate day = row.date().isBefore(today) ? today : row.date();
            dueByDay.merge(day, row.count(), Long::sum);
        }

        dueByDay.merge(today, cardRepository.countWithoutReviewByUserId(userId), Long::sum);

        List<DayCountDto> forecast = new ArrayList<>();
        for (int i = 0; i < FORECAST_DAYS; i++) {
            LocalDate day = today.plusDays(i);
            forecast.add(new DayCountDto(day.toString(), dueByDay.getOrDefault(day, 0L)));
        }

        return forecast;
    }
}
