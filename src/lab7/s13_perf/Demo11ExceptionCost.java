package lab7.s13_perf;

/**
 * Slide 13 — excepțiile nu sunt pentru fluxul normal.
 * Măsurătoare orientativă (nu un benchmark serios): verificare vs. excepție.
 */
public class Demo11ExceptionCost {

    static final int N = 1_000_000;

    static boolean isNumberCheck(String s) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }

    static boolean isNumberException(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        String bad = "abc";
        // încălzire JIT
        for (int i = 0; i < 200_000; i++) { isNumberCheck(bad); isNumberException(bad); }

        long t0 = System.nanoTime();
        int c1 = 0;
        for (int i = 0; i < N; i++) if (!isNumberCheck(bad)) c1++;
        long t1 = System.nanoTime();
        int c2 = 0;
        for (int i = 0; i < N; i++) if (!isNumberException(bad)) c2++;
        long t2 = System.nanoTime();

        System.out.printf("verificare: %4d ms  (%d)%n", (t1 - t0) / 1_000_000, c1);
        System.out.printf("excepție:   %4d ms  (%d)%n", (t2 - t1) / 1_000_000, c2);
        System.out.println("Diferența vine din crearea obiectului și capturarea stack trace-ului.");

        // Pe cazul fericit (input valid, excepția nu se aruncă), try-ul nu costă practic nimic.
    }
}
