package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public class SleeplessNightsFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0);
        }
        SleepingSession first = sleepingSessions.get(0);
        SleepingSession last = sleepingSessions.get(sleepingSessions.size() - 1);

        LocalDate firstDate = first.getStartSleep().toLocalDate();
        LocalDate lastDate = last.getFinishSleep().toLocalDate();


    }
}
