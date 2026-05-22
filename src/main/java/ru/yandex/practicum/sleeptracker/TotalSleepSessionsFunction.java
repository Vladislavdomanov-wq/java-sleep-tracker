package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class TotalSleepSessionsFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        int count;
        count = sessions.size();
        return new SleepAnalysisResult("Колличество сессий сна", count);
    }
}
