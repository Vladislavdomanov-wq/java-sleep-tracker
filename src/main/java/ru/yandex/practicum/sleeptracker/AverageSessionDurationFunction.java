package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;

public class AverageSessionDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Средняя продолжительность сессии (мин) - ";
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        OptionalDouble average = sessions.stream()
                .mapToLong(s -> Duration.between(s.getStartSleep(), s.getFinishSleep()).toMinutes())
                .average();

        double result = average.orElse(0.0);
        return new SleepAnalysisResult(DESCRIPTION, result);
    }
}


