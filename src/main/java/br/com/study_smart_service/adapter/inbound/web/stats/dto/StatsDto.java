package br.com.study_smart_service.adapter.inbound.web.stats.dto;

import java.util.List;

public record StatsDto(
        int currentStreak,
        int longestStreak,
        long totalReviews,
        long reviewsToday,
        List<DayCountDto> activity,
        List<DayCountDto> forecast,
        List<DeckAccuracyDto> deckAccuracy
) {

    public record DayCountDto(String date, long count) {
    }

    public record DeckAccuracyDto(String deckId, String deckTitle, long reviews, long correct) {
    }
}
