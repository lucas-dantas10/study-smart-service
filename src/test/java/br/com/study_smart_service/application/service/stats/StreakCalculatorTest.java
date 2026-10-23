package br.com.study_smart_service.application.service.stats;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StreakCalculatorTest {

    private static final LocalDate TODAY = LocalDate.of(2026, 10, 21);

    @Test
    @DisplayName("Sem nenhum estudo a sequência é zero")
    void shouldBeZeroWithoutStudy() {
        assertEquals(0, StreakCalculator.current(Set.of(), TODAY));
        assertEquals(0, StreakCalculator.longest(Set.of()));
    }

    @Test
    @DisplayName("Conta os dias seguidos incluindo hoje")
    void shouldCountConsecutiveDaysIncludingToday() {
        Set<LocalDate> days = Set.of(TODAY, TODAY.minusDays(1), TODAY.minusDays(2));

        assertEquals(3, StreakCalculator.current(days, TODAY));
    }

    @Test
    @DisplayName("Mantém a sequência de ontem enquanto hoje ainda não foi estudado")
    void shouldKeepYesterdayStreakBeforeStudyingToday() {
        Set<LocalDate> days = Set.of(TODAY.minusDays(1), TODAY.minusDays(2));

        assertEquals(2, StreakCalculator.current(days, TODAY));
    }

    @Test
    @DisplayName("Quebra a sequência quando um dia inteiro fica sem estudo")
    void shouldBreakStreakAfterAMissedDay() {
        Set<LocalDate> days = Set.of(TODAY.minusDays(2), TODAY.minusDays(3));

        assertEquals(0, StreakCalculator.current(days, TODAY));
    }

    @Test
    @DisplayName("Calcula a maior sequência do histórico")
    void shouldFindLongestStreak() {
        List<LocalDate> days = List.of(
                TODAY.minusDays(30), TODAY.minusDays(29), TODAY.minusDays(28), TODAY.minusDays(27),
                TODAY.minusDays(10), TODAY.minusDays(9),
                TODAY
        );

        assertEquals(4, StreakCalculator.longest(days));
    }
}
