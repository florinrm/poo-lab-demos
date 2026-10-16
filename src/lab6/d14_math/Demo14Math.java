package lab6.d14_math;

import java.util.Random;

/**
 * Slide 14 — [Nice to know] Math și câteva rezultate surprinzătoare.
 */
public class Demo14Math {
    public static void main(String[] args) {
        System.out.println("abs(-5)=" + Math.abs(-5) + ", max(3,7)=" + Math.max(3, 7)
                + ", pow(2,10)=" + Math.pow(2, 10) + ", sqrt(16)=" + Math.sqrt(16));
        System.out.println("ceil(1.3)=" + Math.ceil(1.3) + ", floor(1.7)=" + Math.floor(1.7)
                + ", floor(-1.7)=" + Math.floor(-1.7));
        System.out.println("round(2.5)=" + Math.round(2.5) + ", round(-2.5)=" + Math.round(-2.5)
                + "  ← rotunjește spre +∞ la .5");
        System.out.println("sin(PI/2)=" + Math.sin(Math.PI / 2) + ", sin(PI)=" + Math.sin(Math.PI)
                + ", toDegrees(PI)=" + Math.toDegrees(Math.PI));

        // Împărțirea la zero
        try {
            System.out.println(7 / 0);
        } catch (ArithmeticException e) {
            System.out.println("7 / 0 → ArithmeticException: " + e.getMessage());
        }
        System.out.println("7.0 / 0 = " + 7.0 / 0 + ", -7.0 / 0 = " + -7.0 / 0 + ", 0.0 / 0 = " + 0.0 / 0);
        System.out.println("NaN == NaN ? " + (Double.NaN == Double.NaN) + ", isNaN: " + Double.isNaN(0.0 / 0));

        // abs poate întoarce un număr negativ
        System.out.println("abs(Integer.MIN_VALUE) = " + Math.abs(Integer.MIN_VALUE));
        // Variante "exacte": aruncă în loc să facă overflow în tăcere
        try {
            Math.addExact(Integer.MAX_VALUE, 1);
        } catch (ArithmeticException e) {
            System.out.println("addExact(MAX, 1) → ArithmeticException: " + e.getMessage());
        }

        // Numere aleatoare
        System.out.println("random() ∈ [0,1): " + Math.random());
        Random r = new Random(42);          // seed fix → aceeași secvență la fiecare rulare
        System.out.println("Random(42): " + r.nextInt(10) + " " + r.nextInt(10) + " " + r.nextInt(10));

        // DEMO EROARE: decomentează → Math() has private access in Math
        // Math m = new Math();
    }
}
