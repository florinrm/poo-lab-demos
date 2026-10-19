package lab10.d10_capture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Slide 10 — Wildcard capture: o metodă helper generică dă un nume tipului necunoscut.
 */
public class Demo10Capture {

    // Public: acceptă orice listă
    static void rotateLeft(List<?> list) {
        rotateHelper(list);                 // compilatorul "capturează" ? ca T
    }

    // Privat: aici tipul are un nume, deci get și set sunt compatibile
    private static <T> void rotateHelper(List<T> list) {
        if (list.isEmpty()) return;
        T first = list.get(0);
        for (int i = 0; i < list.size() - 1; i++) {
            list.set(i, list.get(i + 1));
        }
        list.set(list.size() - 1, first);
    }

    static void duplicateFirst(List<?> list) {
        dupHelper(list);
    }

    private static <T> void dupHelper(List<T> list) {
        list.add(0, list.get(0));
    }

    public static void main(String[] args) {
        List<String> days = new ArrayList<>(List.of("Lu", "Ma", "Mi", "Jo", "Vi"));
        rotateLeft(days);
        System.out.println("rotateLeft:     " + days);

        List<Integer> nums = new ArrayList<>(List.of(7, 8, 9));
        rotateLeft(nums);
        duplicateFirst(nums);
        System.out.println("rotate + dup:   " + nums);

        // JDK: Collections.swap și reverse primesc List<?>
        Collections.swap(days, 0, 4);
        Collections.reverse(nums);
        System.out.println("Collections:    " + days + " " + nums);

        // DEMO EROARE: decomentează → incompatible types: Object cannot be converted to CAP#1
        // List<?> unknown = days; unknown.set(0, unknown.get(0));
    }
}
