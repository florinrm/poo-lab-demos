package lab6.d16_time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * Slide 16 — [Nice to know] java.time.
 */
public class Demo16Time {
    public static void main(String[] args) {
        LocalDate exam = LocalDate.of(2027, 1, 25);
        System.out.println("examen: " + exam + ", o săptămână înainte: " + exam.minusWeeks(1)
                + ", ziua: " + exam.getDayOfWeek());
        System.out.println("zile între 1 oct 2026 și examen: "
                + ChronoUnit.DAYS.between(LocalDate.of(2026, 10, 1), exam));

        LocalTime lab = LocalTime.of(14, 0);
        System.out.println("lab: " + lab + " → se termină la " + lab.plusMinutes(110));

        LocalDateTime start = LocalDateTime.of(2026, 10, 19, 14, 0);
        System.out.println("LocalDateTime: " + start + " +3h = " + start.plusHours(3));

        // Imutabile: plusDays întoarce un obiect nou
        LocalDate d = LocalDate.of(2026, 1, 31);
        d.plusDays(1);
        System.out.println("după d.plusDays(1) fără atribuire: " + d + "; 31 ian + 1 lună = " + d.plusMonths(1));

        // ZonedDateTime: același moment în alt fus orar
        ZonedDateTime buc = start.atZone(ZoneId.of("Europe/Bucharest"));
        System.out.println("București: " + buc);
        System.out.println("New York:  " + buc.withZoneSameInstant(ZoneId.of("America/New_York")));

        // Ora de vară: 29 martie 2026, 03:00 → 04:00. 03:30 nu există în București
        LocalDateTime missing = LocalDateTime.of(2026, 3, 29, 3, 30);
        System.out.println("03:30 pe 29.03.2026 în București → " + missing.atZone(ZoneId.of("Europe/Bucharest")));

        // Formatare și parsare
        DateTimeFormatter ro = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        System.out.println("format: " + ro.format(start) + ", parse: " + LocalDateTime.parse("01.12.2026 18:30", ro));

        DateTimeFormatter us = DateTimeFormatter.ofPattern("h:mm a MM/dd/yy");
        System.out.println("parse cu Locale.US: " + LocalDateTime.parse("10:30 AM 11/01/23", us.withLocale(Locale.US)));
        try {
            LocalDateTime.parse("10:30 AM 11/01/23", us.withLocale(Locale.forLanguageTag("ro")));
        } catch (DateTimeParseException e) {
            System.out.println("același text cu Locale ro → DateTimeParseException (\"AM\" nu e cuvântul românesc)");
        }

        // Instant: un moment pe axa timpului, în UTC
        Instant now = Instant.now();
        System.out.println("Instant: " + now + ", peste 3 zile: " + now.plus(3, ChronoUnit.DAYS));
        System.out.println("Duration 14:00 → 15:50 = " + Duration.between(lab, lab.plusMinutes(110)));

        // DEMO EROARE: decomentează → DateTimeException la runtime: 30 februarie nu există
        // LocalDate.of(2026, 2, 30);
    }
}
