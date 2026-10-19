package lab10.d13_singleton;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Slide 13 — [Optional] Generic Singleton Factory: merge doar pentru obiecte fără stare.
 */
public class Demo13GenericSingleton {

    // Un container MUTABIL
    static class Box<T> {
        private T value;
        void set(T value) { this.value = value; }
        T get() { return value; }
    }

    // Exemplul de pe pagină: o singură instanță Box pentru toate tipurile
    static class BoxFactory {
        private static final Box<Object> INSTANCE = new Box<>();

        @SuppressWarnings("unchecked")
        static <T> Box<T> getInstance() {
            return (Box<T>) INSTANCE;
        }
    }

    // Varianta corectă: obiectul partajat nu are stare
    private static final UnaryOperator<Object> IDENTITY = t -> t;

    @SuppressWarnings("unchecked")
    static <T> UnaryOperator<T> identity() {
        return (UnaryOperator<T>) IDENTITY;
    }

    public static void main(String[] args) {
        Box<String> s = BoxFactory.getInstance();
        Box<Integer> i = BoxFactory.getInstance();
        System.out.println("același obiect? " + (s == (Object) i));
        s.set("abc");
        try {
            Integer x = i.get();
            System.out.println(x);
        } catch (ClassCastException e) {
            System.out.println("Box partajat: ClassCastException — \"cast-ul sigur\" nu era sigur");
        }

        UnaryOperator<String> upper = identity();
        UnaryOperator<Integer> same = identity();
        System.out.println("identity: " + upper.apply("abc") + ", " + same.apply(42) + ", același obiect? " + (upper == (Object) same));

        // Unde apare cu adevărat în JDK
        System.out.println("emptyList() == emptyList(): " + (Collections.emptyList() == Collections.emptyList()));
        System.out.println("Optional.empty() == Optional.empty(): " + (Optional.empty() == Optional.empty()));
        System.out.println("Stream.empty() == Stream.empty(): " + (Stream.empty() == Stream.empty())
                + "  ← NU e singleton (un stream se consumă o singură dată)");

        List<String> e1 = Collections.emptyList();
        try {
            e1.add("x");
        } catch (UnsupportedOperationException e) {
            System.out.println("emptyList().add → UnsupportedOperationException: de aceea poate fi partajat");
        }

        // DEMO EROARE: decomentează și compilează cu -Xlint:unchecked → warning: [unchecked] unchecked cast
        // Box<Double> unchecked = (Box<Double>) (Box<?>) BoxFactory.getInstance();
    }
}
