package lab6.d05_framework;

import java.util.*;

/**
 * Slide 5 — Același cod, implementări diferite: programăm pe interfață.
 */
public class Demo05Framework {

    // Metoda nu știe și nu îi pasă ce implementare primește
    static void fill(Collection<String> c) {
        c.add("Ion");
        c.add("Ana");
        c.add("Ion");
        c.add("Maria");
        System.out.printf("%-14s size=%d  %s%n", c.getClass().getSimpleName(), c.size(), c);
    }

    public static void main(String[] args) {
        fill(new ArrayList<>());
        fill(new LinkedList<>());
        fill(new HashSet<>());
        fill(new LinkedHashSet<>());
        fill(new TreeSet<>());
        fill(new ArrayDeque<>());
        fill(new PriorityQueue<>());

        // Map e separat: nu e Collection, dar are "vederi" care sunt colecții
        Map<String, Integer> ages = new HashMap<>(Map.of("Ion", 20, "Ana", 21));
        Set<String> keys = ages.keySet();
        Collection<Integer> values = ages.values();
        System.out.println("keySet: " + keys + ", values: " + values);

        System.out.println("Map instanceof Collection? " + (ages instanceof Collection));

        // DEMO EROARE: decomentează → incompatible types: HashMap cannot be converted to Collection
        // Collection<String> wrong = new HashMap<String, Integer>();
    }
}
