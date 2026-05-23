package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;

public class SleeplessNightsFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Количество бессонных ночей - ";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult(DESCRIPTION, 0);
        }
        SleepingSession first = sleepingSessions.get(0);
        SleepingSession last = sleepingSessions.get(sleepingSessions.size() - 1);

        LocalDate firstDate = first.getStartSleep().toLocalDate();
        LocalDate lastDate = last.getFinishSleep().toLocalDate();

        long totalNight = ChronoUnit.DAYS.between(firstDate, lastDate) + 1;
        long nightsWithSleep = sleepingSessions.stream()
                .filter(s -> {
                    LocalTime start = s.getStartSleep().toLocalTime();
                    LocalTime finish = s.getFinishSleep().toLocalTime();
                    return start.isBefore(LocalTime.of(6, 0)) && finish.isAfter(LocalTime.of(0, 0));
                })
                .count();
        long sleepless = totalNight - nightsWithSleep;
        return new SleepAnalysisResult(DESCRIPTION, sleepless);
    }
}
