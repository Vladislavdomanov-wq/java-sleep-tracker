package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Хронотип пользователя", "Нет данных");
        }
        List<SleepingSession> nightSessions = sessions.stream()
                .filter(s -> {
                    LocalTime start = s.getStartSleep().toLocalTime();
                    LocalTime finish = s.getFinishSleep().toLocalTime();
                    return start.isBefore(LocalTime.of(6, 0)) && finish.isAfter(LocalTime.of(0, 0));
                })
                .collect(Collectors.toList());
        long owls = nightSessions.stream()
                .filter(s -> {
                    LocalTime start = s.getStartSleep().toLocalTime();
                    LocalTime finish = s.getFinishSleep().toLocalTime();
                    return start.isAfter(LocalTime.of(23, 0)) && finish.isAfter(LocalTime.of(9, 0));
                })
                .count();

        long javoronok = nightSessions.stream()
                .filter(s -> {
                    LocalTime start = s.getStartSleep().toLocalTime();
                    LocalTime finish = s.getFinishSleep().toLocalTime();
                    return start.isBefore(LocalTime.of(22, 0)) && finish.isBefore(LocalTime.of(7, 0));
                })
                .count();

        long golub = nightSessions.size() - owls - javoronok;

        String result;
        if (owls > javoronok && owls > golub) {
            result = "Сова";
        } else if (javoronok > owls && javoronok > golub) {
            result = "Жаворонок";
        } else {
            result = "Голубь";
        }
        return new SleepAnalysisResult("Хронотип пользователя", result);
    }
}
