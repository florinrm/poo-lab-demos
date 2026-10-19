package lab10.d12_bridge;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Slide 12 — Bridge methods: compilatorul generează o metodă în plus ca suprascrierea să supraviețuiască erasure-ului.
 */
public class Demo12Bridge {

    static class Temperature implements Comparable<Temperature> {
        final double celsius;
        Temperature(double celsius) { this.celsius = celsius; }

        @Override
        public int compareTo(Temperature other) {        // în sursă: o singură metodă
            return Double.compare(celsius, other.celsius);
        }

        @Override
        public String toString() { return celsius + "°C"; }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})   // pentru linia cu raw type de mai jos
    public static void main(String[] args) {
        // După erasure, Comparable are compareTo(Object). Clasa noastră are compareTo(Temperature).
        // Fără o punte, n-ar mai suprascrie nimic.
        Arrays.stream(Temperature.class.getDeclaredMethods())
                .filter(m -> m.getName().equals("compareTo"))
                .sorted(Comparator.comparing(Method::isBridge))
                .forEach(m -> System.out.printf("%-58s bridge=%b synthetic=%b%n",
                        m.toGenericString(), m.isBridge(), m.isSynthetic()));

        Temperature[] week = {new Temperature(12.5), new Temperature(-3), new Temperature(8)};
        Arrays.sort(week);            // Arrays.sort apelează compareTo(Object) → bridge → compareTo(Temperature)
        System.out.println("sortat: " + Arrays.toString(week));

        // Bridge-ul face cast; prin raw type ajungem la el cu alt tip
        Comparable raw = new Temperature(20);
        try {
            raw.compareTo("douăzeci");
        } catch (ClassCastException e) {
            System.out.println("raw.compareTo(String) → ClassCastException (aruncat de bridge, la cast)");
        }

        // DEMO EROARE: decomentează → incompatible types: String cannot be converted to Temperature
        // new Temperature(20).compareTo("douăzeci");
    }
}
