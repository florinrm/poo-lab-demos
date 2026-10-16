package lab6.d07_set;

import java.util.*;

/**
 * Slide 7 — HashSet vs LinkedHashSet vs TreeSet, plus navigare în TreeSet.
 */
public class Demo07Set {
    public static void main(String[] args) {
        List<String> input = List.of("Timisoara", "Cluj", "Iasi", "Arad", "Cluj", "Brasov");
        System.out.println("input:         " + input);
        System.out.println("HashSet:       " + new HashSet<>(input));
        System.out.println("LinkedHashSet: " + new LinkedHashSet<>(input));
        System.out.println("TreeSet:       " + new TreeSet<>(input));

        // add întoarce false la duplicat
        Set<String> s = new HashSet<>();
        System.out.println("add Cluj: " + s.add("Cluj") + ", din nou: " + s.add("Cluj"));

        // TreeSet = NavigableSet: căutări "în jurul" unei valori
        TreeSet<Integer> grades = new TreeSet<>(List.of(4, 5, 7, 8, 10));
        System.out.println("floor(6)   = " + grades.floor(6));     // cel mai mare <= 6
        System.out.println("ceiling(6) = " + grades.ceiling(6));   // cel mai mic >= 6
        System.out.println("higher(8)  = " + grades.higher(8));    // strict > 8
        System.out.println("headSet(7) = " + grades.headSet(7));   // < 7
        System.out.println("tailSet(7) = " + grades.tailSet(7));   // >= 7
        System.out.println("descending = " + grades.descendingSet());

        // null: HashSet acceptă, TreeSet (ordine naturală) nu
        Set<String> withNull = new HashSet<>();
        withNull.add(null);
        System.out.println("HashSet cu null: " + withNull);
        try {
            new TreeSet<String>().add(null);
        } catch (NullPointerException e) {
            System.out.println("TreeSet.add(null): NullPointerException");
        }

        // DEMO EROARE: decomentează → ClassCastException la runtime: Object nu e Comparable
        // new TreeSet<Object>().add(new Object());
    }
}
