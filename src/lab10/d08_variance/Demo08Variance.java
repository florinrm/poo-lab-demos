package lab10.d08_variance;

import java.util.ArrayList;
import java.util.List;

/**
 * Slide 8 — Invarianță: List&lt;Integer&gt; NU e List&lt;Number&gt;. Array-urile sunt covariante (și plătesc la runtime).
 */
public class Demo08Variance {

    static double total(List<Number> values) {          // acceptă DOAR List<Number>
        double s = 0;
        for (Number n : values) s += n.doubleValue();
        return s;
    }

    static double totalAny(List<? extends Number> values) {   // acceptă orice listă de subtipuri
        double s = 0;
        for (Number n : values) s += n.doubleValue();
        return s;
    }

    public static void main(String[] args) {
        List<Integer> ints = new ArrayList<>(List.of(1, 2, 3));
        List<Number> nums = new ArrayList<>(List.of(1, 2.5));

        System.out.println("total(nums)    = " + total(nums));
        System.out.println("totalAny(ints) = " + totalAny(ints));
        System.out.println("totalAny(nums) = " + totalAny(nums));

        // De ce interdicția? Dacă List<Integer> ar fi List<Number>:
        //     List<Number> alias = ints;  alias.add(2.5);  int x = ints.get(3);  // un Double în List<Integer>!

        // Array-urile SUNT covariante: String[] e un Object[]
        Object[] arr = new String[2];
        arr[0] = "ok";
        try {
            arr[1] = 42;                                  // compilează...
        } catch (ArrayStoreException e) {
            System.out.println("arr[1] = 42 → ArrayStoreException: " + e.getMessage());
        }
        // ...dar array-ul își știe tipul la runtime (reified) și verifică fiecare scriere.
        // Generics nu au cum: tipul e șters (erasure), deci compilatorul interzice de la început.

        // DEMO EROARE: decomentează → incompatible types: List<Integer> cannot be converted to List<Number>
        // System.out.println(total(ints));
    }
}
