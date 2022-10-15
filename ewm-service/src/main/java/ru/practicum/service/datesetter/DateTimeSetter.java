package ru.practicum.service.datesetter;

import java.time.LocalDateTime;

/**
 * Class for setting the current date and time
 */
public class DateTimeSetter {
    /**
     * Method of setting the current date and time
     * @return LocalDateTime object contains the current date and time
     */
    public static LocalDateTime setDateTime() {
        return LocalDateTime.now().withNano(0);
    }
}
