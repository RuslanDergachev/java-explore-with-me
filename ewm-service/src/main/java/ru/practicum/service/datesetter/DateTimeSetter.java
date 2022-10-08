package ru.practicum.service.datesetter;

import java.time.LocalDateTime;

public class DateTimeSetter {
    public static LocalDateTime setDateTime() {
        return LocalDateTime.now().withNano(0);
    }
}
