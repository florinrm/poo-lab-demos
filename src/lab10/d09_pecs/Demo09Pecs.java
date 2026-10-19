package lab10.d09_pecs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Slide 9 — Wildcards și PECS: Producer Extends, Consumer Super.
 */
public class Demo09Pecs {

    // ? : nu ne interesează tipul, doar citim ca Object
    static void printAll(List<?> list) {
        for (Object o : list) System.out.print(o + " ");
        System.out.println();
    }

    // Producer: din src doar CITIM → extends
    static double sum(Collection<? extends Number> src) {
        double s = 0;
        for (Number n : src) s += n.doubleValue();
        return s;
    }

    // Consumer: în dst doar SCRIEM → super
    static void fillWithOnes(List<? super Integer> dst, int n) {
        for (int i = 0; i < n; i++) dst.add(1);
    }

    // Ambele: copiem din producer în consumer
    static <T> void copy(List<? super T> dst, List<? extends T> src) {
        for (T x : src) dst.add(x);
    }

    public static void main(String[] args) throws Exception {
        List<Integer> ints = List.of(1, 2, 3);
        List<Double> doubles = List.of(0.5, 0.25);

        printAll(ints);
        printAll(List.of("a", "b"));

        System.out.println("sum ints = " + sum(ints) + ", sum doubles = " + sum(doubles));

        List<Number> numbers = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        fillWithOnes(numbers, 2);
        fillWithOnes(objects, 3);
        System.out.println("fillWithOnes: " + numbers + " " + objects);

        copy(numbers, ints);       // T = Integer: Integer → Number
        copy(numbers, doubles);    // T = Double
        copy(objects, List.of("text"));
        System.out.println("copy: " + numbers + " " + objects);

        // Ce citim dintr-o listă "? super Integer"? Doar Object.
        List<? super Integer> sink = numbers;
        Object first = sink.get(0);
        System.out.println("din ? super Integer citim doar Object: " + first);

        // JDK-ul folosește exact regula asta
        System.out.println(Collections.class.getMethod("copy", List.class, List.class).toGenericString());
        System.out.println(Collections.class.getMethod("max", Collection.class).toGenericString());

        // DEMO EROARE: decomentează → incompatible types: int cannot be converted to CAP#1 (nu știm exact subtipul)
        // List<? extends Number> producer = ints; producer.add(4);
    }
}
