package lab11.d09_intermediate;

import lab11.data.Movie;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Slide 9 — Operații intermediare: filter, map, flatMap, distinct, sorted, limit, skip, takeWhile.
 */
public class Demo09Intermediate {
    public static void main(String[] args) {
        List<Movie> movies = Movie.catalog();

        // filter + map
        List<String> thrillers = movies.stream()
                .filter(m -> m.genre().equals("Thriller"))
                .map(Movie::title)
                .toList();
        System.out.println("thrillere:       " + thrillers);

        // map: fiecare film → O listă de actori. Rezultatul e o listă de liste
        List<List<String>> nested = movies.stream().limit(3).map(Movie::actors).toList();
        System.out.println("map(actors):     " + nested);

        // flatMap: fiecare film → un STREAM de actori, apoi "lipite" într-unul singur
        List<String> flat = movies.stream().limit(3).flatMap(m -> m.actors().stream()).toList();
        System.out.println("flatMap(actors): " + flat);

        // distinct: actori care apar în mai multe filme, fără duplicate
        long actors = movies.stream().flatMap(m -> m.actors().stream()).count();
        long uniqueActors = movies.stream().flatMap(m -> m.actors().stream()).distinct().count();
        System.out.println("actori: " + actors + " apariții, " + uniqueActors + " distincți");

        // sorted cu Comparator compus, apoi limit
        List<String> top3 = movies.stream()
                .sorted(Comparator.comparingDouble(Movie::rating).reversed().thenComparing(Movie::title))
                .limit(3)
                .map(m -> m.title() + " " + m.rating())
                .toList();
        System.out.println("top 3:           " + top3);

        // skip + limit = paginare
        List<String> page2 = movies.stream().map(Movie::title).sorted().skip(3).limit(3).toList();
        System.out.println("pagina 2 (3/pag): " + page2);

        // takeWhile / dropWhile (Java 9): se opresc la primul element care nu respectă condiția
        List<Integer> temps = List.of(2, 4, 7, 12, 3, 1);
        System.out.println("takeWhile(<10):  " + temps.stream().takeWhile(t -> t < 10).toList()
                + "  vs filter(<10): " + temps.stream().filter(t -> t < 10).toList());

        // mapToObj / map pe tipuri diferite
        System.out.println("lungimi titluri: " + Stream.of("Up", "Se7en", "Parasite").map(String::length).toList());

        // DEMO EROARE: decomentează → incompatible types: List<List<String>> cannot be converted to List<String> (aici trebuia flatMap)
        // List<String> wrong = movies.stream().map(Movie::actors).toList();
    }
}
