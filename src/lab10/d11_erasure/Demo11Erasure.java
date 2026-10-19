package lab10.d11_erasure;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Slide 11 — Type erasure: ce se pierde la runtime și cum ocolim.
 */
public class Demo11Erasure {

    // Câmp cu tip parametrizat: declarația își păstrează tipul în metadate
    static List<String> declared = new ArrayList<>();

    // Nu putem scrie new T() — dar putem primi "rețeta" de construcție
    static <T> List<T> fill(int n, Supplier<T> factory) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < n; i++) result.add(factory.get());
        return result;
    }

    // Nici new T[n] — dar Class<T> știe tipul la runtime
    @SuppressWarnings("unchecked")
    static <T> T[] newArray(Class<T> type, int n) {
        return (T[]) Array.newInstance(type, n);
    }

    static <T> T create(Class<T> type) throws Exception {
        return type.getDeclaredConstructor().newInstance();
    }

    static class Calculator {
        static void describe(List<String> words) { System.out.println("   listă de cuvinte"); }

        // DEMO EROARE: decomentează → name clash: describe(List<Integer>) and describe(List<String>) have the same erasure
        // static void describe(List<Integer> nums) { System.out.println("   listă de numere"); }
    }

    public static void main(String[] args) throws Exception {
        List<String> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        System.out.println("aceeași clasă la runtime? " + (a.getClass() == b.getClass()) + " → " + a.getClass().getName());

        // instanceof cu wildcard: permis; cu List<String> (pe Object): interzis
        Object o = a;
        System.out.println("o instanceof List<?> = " + (o instanceof List<?>));

        // Ce rămâne totuși: tipul declarat al câmpului e în bytecode
        System.out.println("tipul declarat al câmpului: " + Demo11Erasure.class.getDeclaredField("declared").getGenericType());

        List<StringBuilder> builders = fill(3, StringBuilder::new);
        System.out.println("fill cu Supplier: " + builders.size() + " × " + builders.get(0).getClass().getSimpleName());

        String[] arr = newArray(String.class, 4);
        System.out.println("newArray(String.class): " + arr.getClass().getSimpleName() + " de lungime " + arr.length);

        StringBuilder sb = create(StringBuilder.class).append("creat prin Class<T>");
        System.out.println(sb);

        Calculator.describe(List.of("x"));
    }
}
