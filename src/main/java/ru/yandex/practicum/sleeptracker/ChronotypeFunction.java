package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Хронотип пользователя";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(DESCRIPTION, "Нет данных");
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

        Chronotype result;
        if (owls > javoronok && owls > golub) {
           result = Chronotype.OWL;
        } else if (javoronok > owls && javoronok > golub) {
            result = Chronotype.LARK;
        } else {
            result = Chronotype.DOVE;
        }
        return new SleepAnalysisResult(DESCRIPTION, result.getDisplayName());
    }
}
