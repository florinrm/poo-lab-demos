package lab12.d09_assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Slide 9 — Assertions: verificări de ipoteze interne, oprite implicit.
 * Rulați o dată normal și o dată cu -ea (IntelliJ: Run → Edit Configurations → VM options: -ea).
 */
public class Demo09Assertions {

    // Validarea argumentelor unei metode publice: excepție, mereu activă
    public static int divide(int a, int b) {
        if (b == 0) throw new IllegalArgumentException("b must not be zero");
        return a / b;
    }

    // Ipoteză internă: după sortare, tabloul TREBUIE să fie sortat
    private static int[] sortedCopy(int[] a) {
        int[] r = a.clone();
        Arrays.sort(r);
        assert isSorted(r) : "sort broken: " + Arrays.toString(r);
        return r;
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i - 1] > a[i]) return false;
        return true;
    }

    // O metodă "buggy": promite să întoarcă un număr par
    private static int nextEven(int x) {
        int r = x + 1;                                     // bug: ar trebui x + (x % 2 == 0 ? 2 : 1)
        assert r % 2 == 0 : "nextEven(" + x + ") = " + r + " nu e par";
        return r;
    }

    public static void main(String[] args) {
        boolean enabled = Demo09Assertions.class.desiredAssertionStatus();
        System.out.println("assertions activate? " + enabled + (enabled ? "" : "  (rulați și cu -ea)"));

        System.out.println("sortedCopy: " + Arrays.toString(sortedCopy(new int[]{5, 1, 4})));

        try {
            divide(7, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("divide(7, 0) → IllegalArgumentException, cu sau fără -ea");
        }

        System.out.println("nextEven(5) = " + nextEven(5));                   // 6, ok
        try {
            System.out.println("nextEven(4) = " + nextEven(4));               // bug: 5
        } catch (AssertionError e) {
            System.out.println("AssertionError: " + e.getMessage());
        }

        // Capcană: assert cu efect secundar — fără -ea, efectul DISPARE
        List<String> queue = new ArrayList<>(List.of("a", "b"));
        assert queue.remove("a");
        System.out.println("după assert queue.remove(\"a\"): " + queue + (enabled ? "" : "  ← \"a\" n-a fost șters!"));

        // DEMO EROARE: decomentează și rulează CU -ea → AssertionError: assert-ul pică, fără -ea nu se întâmplă nimic
        // assert queue.isEmpty() : "coada trebuia să fie goală";
    }
}
