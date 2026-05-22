package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MaxSessionDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Optional<Long> max = sessions.stream()
                .map(s -> Duration.between(s.getStartSleep(), s.getFinishSleep()).toMinutes())
                .max(Long::compare);

        long result = max.orElse(0L);
        return new SleepAnalysisResult("Максимальная продолжительность сессии (мин)", result);
    }
}
