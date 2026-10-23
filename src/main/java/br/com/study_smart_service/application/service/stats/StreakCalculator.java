package br.com.study_smart_service.application.service.stats;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

final class StreakCalculator {

    private StreakCalculator() {
    }

    /**
     * Dias seguidos estudando até hoje. Se hoje ainda não teve revisão, a sequência de ontem
     * continua valendo: ela só quebra quando um dia inteiro passa sem estudo.
     */
    static int current(Set<LocalDate> studyDays, LocalDate today) {
        LocalDate day = studyDays.contains(today) ? today : today.minusDays(1);
        int streak = 0;

        while (studyDays.contains(day)) {
            streak++;
            day = day.minusDays(1);
        }

        return streak;
    }

    static int longest(Collection<LocalDate> studyDays) {
        int best = 0;
        int run = 0;
        LocalDate previous = null;

        for (LocalDate day : new TreeSet<>(studyDays)) {
            run = previous != null && previous.plusDays(1).equals(day) ? run + 1 : 1;
            best = Math.max(best, run);
            previous = day;
        }

        return best;
    }
}
