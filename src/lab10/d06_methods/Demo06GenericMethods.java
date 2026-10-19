package lab10.d06_methods;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Slide 6 — Metode generice și inferența tipurilor.
 */
public class Demo06GenericMethods {

    // <T> înaintea tipului de return: metoda își declară propriul parametru de tip
    static <T> T firstOrDefault(List<T> list, T fallback) {
        return list.isEmpty() ? fallback : list.get(0);
    }

    static <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    static <K, V> Map<V, K> invert(Map<K, V> map) {
        Map<V, K> result = new HashMap<>();
        for (Map.Entry<K, V> e : map.entrySet()) result.put(e.getValue(), e.getKey());
        return result;
    }

    // T e dedus din AMBELE argumente
    static <T> T pick(boolean firstOne, T a, T b) {
        return firstOne ? a : b;
    }

    public static void main(String[] args) {
        System.out.println(firstOrDefault(List.of("Ana", "Ion"), "nimeni"));
        System.out.println(firstOrDefault(List.<Integer>of(), -1));

        String[] names = {"Ana", "Ion", "Maria"};
        swap(names, 0, 2);
        System.out.println("swap: " + Arrays.toString(names));

        Map<String, Integer> rooms = Map.of("POO", 303, "SD", 105);
        System.out.println("invert: " + invert(rooms).get(303));

        // Tipul explicit (type witness), rar necesar
        List<String> empty = Demo06GenericMethods.<String>emptyAndPrint();
        System.out.println("witness: " + empty);

        // Inferența caută cel mai specific tip comun
        var x = pick(true, "text", 42);
        System.out.println("pick(\"text\", 42) → tip static dedus: Serializable & Comparable<...>, valoare: " + x);
        Object o = pick(false, "text", 42);
        System.out.println("ca Object: " + o + " (" + o.getClass().getSimpleName() + ")");

        // DEMO EROARE: decomentează → incompatible types: inferred type does not conform to upper bound(s)
        // String s = pick(true, "text", 42);
    }

    static <T> List<T> emptyAndPrint() {
        System.out.println("   (emptyAndPrint apelat)");
        return List.of();
    }
}
