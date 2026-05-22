package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {
    SleepingSession session1 = new SleepingSession(
            LocalDateTime.of(2025, 10, 1, 22, 15),
            LocalDateTime.of(2025, 10, 2, 6, 30),
            SleepQuality.GOOD
    );

    SleepingSession session2 = new SleepingSession(
            LocalDateTime.of(2025, 10, 1, 0, 15),
            LocalDateTime.of(2025, 10, 1, 3, 30),
            SleepQuality.BAD
    );

    SleepingSession session3 = new SleepingSession(
            LocalDateTime.of(2025, 10, 1, 23, 15),
            LocalDateTime.of(2025, 10, 2, 6, 30),
            SleepQuality.NORMAL
    );

    @Test
    void shouldReturnTotalSessionsCount() {

        List<SleepingSession> sessions = List.of(session1, session2, session3);
        TotalSleepSessionsFunction function = new TotalSleepSessionsFunction();

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(3, result.getValue());
        assertEquals("Колличество сессий сна", result.getDescription());
    }

    @Test
    void shouldReturnZeroForEmptyList() {
        List<SleepingSession> empty = List.of();
        TotalSleepSessionsFunction function = new TotalSleepSessionsFunction();
        SleepAnalysisResult result = function.apply(empty);
        assertEquals(0, result.getValue());
    }

    @Test
    void shouldMinSessionForEmptyList() {
        List<SleepingSession> sessions = List.of(session1, session2, session3);
        MinSessionDurationFunction function = new MinSessionDurationFunction();

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(195L, result.getValue());
        assertEquals("Минимальная продолжительность сессии (мин)", result.getDescription());
    }

    @Test
    void shouldReturnMaxSessionDuration() {
        List<SleepingSession> sessions = List.of(session1, session2, session3);
        MaxSessionDurationFunction function = new MaxSessionDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(495L, result.getValue());
    }

    @Test
    void shouldReturnZeroMaxForEmptyList() {
        MaxSessionDurationFunction function = new MaxSessionDurationFunction();
        SleepAnalysisResult result = function.apply(List.of());
        assertEquals(0L, result.getValue());
    }

    @Test
    void shouldReturnAverageSessionDuration() {
        List<SleepingSession> sessions = List.of(session1, session2, session3);
        AverageSessionDurationFunction function = new AverageSessionDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals((495.0 + 195.0 + 435.0) / 3, (double) result.getValue(), 0.01);
    }

    @Test
    void shouldReturnZeroAverageForEmptyList() {
        AverageSessionDurationFunction function = new AverageSessionDurationFunction();
        SleepAnalysisResult result = function.apply(List.of());
        assertEquals(0.0, (double) result.getValue(), 0.01);
    }

    @Test
    void shouldReturnBadSessionsCount() {
        List<SleepingSession> sessions = List.of(session1, session2, session3);
        BadSessionDurationFunction function = new BadSessionDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(1L, result.getValue()); // только session2 — BAD
    }

    @Test
    void shouldReturnZeroBadForEmptyList() {
        BadSessionDurationFunction function = new BadSessionDurationFunction();
        SleepAnalysisResult result = function.apply(List.of());
        assertEquals(0L, result.getValue());
    }
    @Test
    void shouldReturnSleeplessNightsWhenDaySessionOnly() {
        // сессия только днём — не задевает 0:00-6:00
        SleepingSession daySession = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 14, 0),
                LocalDateTime.of(2025, 10, 1, 15, 0),
                SleepQuality.NORMAL
        );
        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.apply(List.of(daySession));
        assertEquals(1L, result.getValue());
    }

    @Test
    void shouldReturnSleeplessNightsWhenMixedSessions() {
        // ночная + дневная сессии
        SleepingSession nightSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 0),
                LocalDateTime.of(2025, 10, 2, 7, 0),
                SleepQuality.GOOD
        );
        SleepingSession daySession = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 14, 0),
                LocalDateTime.of(2025, 10, 1, 15, 0),
                SleepQuality.NORMAL
        );
        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.apply(List.of(nightSession, daySession));
        assertEquals(1L, result.getValue());
    }

}

