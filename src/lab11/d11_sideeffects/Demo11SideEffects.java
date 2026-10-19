package lab11.d11_sideeffects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Slide 11 — Side effects și parallel streams.
 */
public class Demo11SideEffects {
    public static void main(String[] args) {
        List<Integer> numbers = IntStream.rangeClosed(1, 100_000).boxed().toList();

        // 1. Side effect: adăugăm într-o listă externă din forEach — pe paralel se pierd elemente
        for (int run = 1; run <= 3; run++) {
            List<Integer> out = new ArrayList<>();
            try {
                numbers.parallelStream().forEach(out::add);          // ArrayList nu e thread-safe
                System.out.println("forEach(out::add), rularea " + run + ": " + out.size() + " elemente (așteptat 100000)");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("forEach(out::add), rularea " + run + ": ArrayIndexOutOfBoundsException");
            }
        }

        // Corect: lăsăm stream-ul să construiască rezultatul
        List<Integer> collected = numbers.parallelStream().map(x -> x).toList();
        System.out.println("toList() în paralel: " + collected.size() + " elemente, ordinea păstrată: "
                + collected.subList(0, 5));

        // 2. Sumă: fără stare externă
        long sum = numbers.parallelStream().mapToLong(x -> x).sum();
        System.out.println("sum() paralel = " + sum);

        // 3. reduce cere o operație ASOCIATIVĂ; scăderea nu e
        int seq = IntStream.rangeClosed(1, 10).reduce(0, (a, b) -> a - b);
        int par = IntStream.rangeClosed(1, 10).parallel().reduce(0, (a, b) -> a - b);
        System.out.println("reduce(a - b): secvențial = " + seq + ", paralel = " + par + "  ← rezultate diferite");

        // 4. forEach vs forEachOrdered
        System.out.print("forEach paralel:        ");
        IntStream.rangeClosed(1, 10).parallel().forEach(x -> System.out.print(x + " "));
        System.out.print("\nforEachOrdered paralel: ");
        IntStream.rangeClosed(1, 10).parallel().forEachOrdered(x -> System.out.print(x + " "));
        System.out.println();

        // DEMO EROARE: decomentează → ConcurrentModificationException la runtime: nu modificați sursa din pipeline
        // List<Integer> src = new ArrayList<>(List.of(1, 2, 3)); src.stream().forEach(x -> src.add(x * 10));
    }
}
