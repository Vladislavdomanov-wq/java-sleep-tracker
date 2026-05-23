package ru.yandex.practicum.sleeptracker;


import java.util.List;
import java.util.function.Function;

public class BadSessionDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Кол-во сессии с плохим сном - ";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long count = sessions.stream()
                .filter(s -> s.getQuality() == SleepQuality.BAD)
                .count();


        return new SleepAnalysisResult(DESCRIPTION, count);
    }
}


