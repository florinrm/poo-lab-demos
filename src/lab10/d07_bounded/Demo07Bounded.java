package lab10.d07_bounded;

import java.time.LocalDate;
import java.util.List;

/**
 * Slide 7 — Bounded generics: T extends ... ne permite să apelăm metode pe T.
 */
public class Demo07Bounded {

    // Fără bound, pe T am putea apela doar metodele din Object
    static <T extends Number> double sum(List<T> values) {
        double s = 0;
        for (T v : values) s += v.doubleValue();       // merge: T e cel puțin Number
        return s;
    }

    // Forma pe care o veți vedea des...
    static <T extends Comparable<T>> T maxStrict(List<T> list) {
        T best = list.get(0);
        for (T x : list) if (x.compareTo(best) > 0) best = x;
        return best;
    }

    // ...și forma corectă, cea din JDK (Collections.max)
    static <T extends Comparable<? super T>> T max(List<T> list) {
        T best = list.get(0);
        for (T x : list) if (x.compareTo(best) > 0) best = x;
        return best;
    }

    // Limitări multiple: întâi clasa, apoi interfețele
    static <T extends Number & Comparable<T>> T clampMax(T value, T limit) {
        return value.compareTo(limit) > 0 ? limit : value;
    }

    static class NumberBox<T extends Number> {
        private final T value;
        NumberBox(T value) { this.value = value; }
        boolean isPositive() { return value.doubleValue() > 0; }
    }

    public static void main(String[] args) {
        System.out.println("sum ints:    " + sum(List.of(1, 2, 3)));
        System.out.println("sum doubles: " + sum(List.of(1.5, 2.5)));

        System.out.println("maxStrict:   " + maxStrict(List.of("pere", "mere", "zmeura")));
        System.out.println("max:         " + max(List.of("pere", "mere", "zmeura")));

        // LocalDate implementează Comparable<ChronoLocalDate>, NU Comparable<LocalDate>
        List<LocalDate> exams = List.of(LocalDate.of(2027, 1, 25), LocalDate.of(2027, 2, 3), LocalDate.of(2027, 1, 19));
        System.out.println("max dată:    " + max(exams) + "  ← merge doar cu Comparable<? super T>");

        System.out.println("clampMax:    " + clampMax(120, 100) + ", " + clampMax(3.5, 10.0));
        System.out.println("NumberBox:   " + new NumberBox<>(-4).isPositive() + ", " + new NumberBox<>(2.5).isPositive());

        // DEMO EROARE: decomentează → method maxStrict cannot be applied to given types (inferred type does not conform to upper bound(s)): LocalDate nu e Comparable<LocalDate>
        // System.out.println(maxStrict(exams));
    }
}
