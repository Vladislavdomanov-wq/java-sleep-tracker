package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MinSessionDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Optional<Long> min = sessions.stream()
                .map(s -> Duration.between(s.getStartSleep(), s.getFinishSleep()).toMinutes())
                .min(Long::compare);

        long result = min.orElse(0L);
        return new SleepAnalysisResult("Минимальная продолжительность сессии (мин)", result);
    }
}