package ru.yandex.practicum.sleeptracker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {

    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Ошибка, укажите путь к файлу");
            return;
        }
        String filePath = args[0];

        List<Function<List<SleepingSession>, SleepAnalysisResult>> functions = new ArrayList<>();

        functions.add(new TotalSleepSessionsFunction());
        functions.add(new MinSessionDurationFunction());
        functions.add(new MaxSessionDurationFunction());
        functions.add(new AverageSessionDurationFunction());
        functions.add(new BadSessionDurationFunction());
        functions.add(new SleeplessNightsFunction());
        functions.add(new ChronotypeFunction());

        try {
            SleepTrackerAppLoader loader = new SleepTrackerAppLoader();
            List<SleepingSession> sessions = loader.load(filePath);
            functions.forEach(f -> {
                SleepAnalysisResult result = f.apply(sessions);
                System.out.println(result.getDescription() + ": " + result.getValue());
            });

        } catch (SleepTrackerAppLoaderException e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
        }

    }

}