package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime startSleep;
    private final LocalDateTime finishSleep;
    private final SleepQuality quality;

    public SleepingSession(LocalDateTime startSleep, LocalDateTime finishSleep, SleepQuality quality) {
        this.startSleep = startSleep;
        this.finishSleep = finishSleep;
        this.quality = quality;
    }

    public LocalDateTime getStartSleep() {
        return startSleep;
    }

    public LocalDateTime getFinishSleep() {
        return finishSleep;
    }

    public SleepQuality getQuality() {
        return quality;
    }


    @Override
    public String toString() {
        return "SleepingSession{" +
                "startSleep=" + startSleep +
                ", finishSleep=" + finishSleep +
                ", quality=" + quality +
                '}';
    }
}
