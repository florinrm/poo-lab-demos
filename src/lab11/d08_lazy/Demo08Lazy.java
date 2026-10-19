package lab11.d08_lazy;

import java.util.List;
import java.util.stream.Stream;

/**
 * Slide 8 — Cum se execută un stream: lazy, element cu element, o singură dată.
 */
public class Demo08Lazy {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

        // 1. Fără operație terminală nu se execută nimic
        System.out.println("1) pipeline fără terminală:");
        Stream<Integer> pipeline = numbers.stream()
                .filter(x -> { System.out.println("   filter " + x); return x % 2 == 0; });
        System.out.println("   (nimic afișat până acum)");

        // 2. Abia count() pornește execuția
        System.out.println("2) după count(): " + pipeline.count());

        // 3. Ordinea: fiecare element trece prin TOT pipeline-ul înainte de următorul
        System.out.println("3) ordinea execuției:");
        numbers.stream()
                .filter(x -> { System.out.println("   filter " + x); return x % 2 == 0; })
                .map(x -> { System.out.println("   map    " + x); return x * 10; })
                .forEach(x -> System.out.println("   → " + x));

        // 4. Short-circuit: anyMatch se oprește la prima potrivire
        System.out.println("4) anyMatch(x > 2):");
        boolean found = numbers.stream()
                .peek(x -> System.out.println("   verific " + x))
                .anyMatch(x -> x > 2);
        System.out.println("   găsit: " + found + " (4, 5, 6 nu au fost vizitate)");

        // 5. Stream infinit: merge doar pentru că limit() oprește procesarea
        List<Integer> powers = Stream.iterate(1, x -> x * 2).limit(10).toList();
        System.out.println("5) primele 10 puteri ale lui 2: " + powers);

        // 6. sorted() trebuie să vadă tot: e o operație "cu stare" și strică ordinea verticală
        System.out.println("6) cu sorted() la mijloc:");
        Stream.of(3, 1, 2)
                .peek(x -> System.out.println("   înainte de sorted " + x))
                .sorted()
                .forEach(x -> System.out.println("   după sorted      " + x));

        // DEMO EROARE: decomentează → IllegalStateException la runtime: stream has already been operated upon or closed
        // pipeline.count();
    }
}
