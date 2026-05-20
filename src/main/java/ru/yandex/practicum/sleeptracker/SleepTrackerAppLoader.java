package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SleepTrackerAppLoader {


    public List<SleepingSession> load(String filePath) throws SleepTrackerAppLoaderException {

        List<SleepingSession> tracker = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                LocalDateTime startSleep = LocalDateTime.parse(parts[0], formatter);
                LocalDateTime finishSleep = LocalDateTime.parse(parts[1], formatter);
                SleepQuality sleepQuality = SleepQuality.valueOf(parts[2]);
                SleepingSession sleepingSession = new SleepingSession(startSleep , finishSleep , sleepQuality);
                tracker.add(sleepingSession);
            }
        } catch (IOException e) {
            throw new SleepTrackerAppLoaderException("Ошибка чтения файла", e);
        }

        if (tracker.isEmpty()) {
            throw new SleepTrackerAppLoaderException("трекер пустой");
        }
        return tracker;
    }
}

