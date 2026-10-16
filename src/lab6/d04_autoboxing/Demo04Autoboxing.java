package lab6.d04_autoboxing;

import java.util.ArrayList;
import java.util.List;

/**
 * Slide 4 — Autoboxing / unboxing și cele trei capcane.
 */
public class Demo04Autoboxing {
    public static void main(String[] args) {
        List<Integer> xs = new ArrayList<>();
        xs.add(5);              // autoboxing: compilatorul scrie Integer.valueOf(5)
        int y = xs.get(0);      // unboxing: compilatorul scrie xs.get(0).intValue()
        System.out.println("y = " + y);

        // Capcana 1: == compară referințe. Integer.valueOf ține un cache pentru [-128, 127]
        Integer a = 127, b = 127;
        Integer c = 128, d = 128;
        System.out.println("127 == 127 ? " + (a == b));
        System.out.println("128 == 128 ? " + (c == d));
        System.out.println("128 equals 128 ? " + c.equals(d));

        // Capcana 2: unboxing pe null → NullPointerException
        Integer missing = null;
        try {
            int k = missing;
            System.out.println(k);
        } catch (NullPointerException e) {
            System.out.println("NPE la unboxing: " + e.getMessage());
        }

        // Capcana 3: boxing în bucle mari
        long t0 = System.nanoTime();
        Long sumBoxed = 0L;
        for (int i = 0; i < 20_000_000; i++) sumBoxed += i;     // un Long nou la fiecare pas
        long t1 = System.nanoTime();
        long sumPrim = 0L;
        for (int i = 0; i < 20_000_000; i++) sumPrim += i;
        long t2 = System.nanoTime();
        System.out.printf("Long: %d ms, long: %d ms (aceeași sumă: %b)%n",
                (t1 - t0) / 1_000_000, (t2 - t1) / 1_000_000, sumBoxed == sumPrim);

        // DEMO EROARE: decomentează → incompatible types: <null> cannot be converted to int
        // int z = null;
    }
}
