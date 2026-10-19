package lab11.d10_terminal;

import lab11.data.Movie;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Slide 10 — Operații terminale și Collectors.
 */
public class Demo10Terminal {
    public static void main(String[] args) {
        List<Movie> movies = Movie.catalog();

        // count, anyMatch / allMatch / noneMatch
        System.out.println("filme după 2010: " + movies.stream().filter(m -> m.year() > 2010).count());
        System.out.println("vreun film sub 7? " + movies.stream().anyMatch(m -> m.rating() < 7)
                + ", toate peste 90 min? " + movies.stream().allMatch(m -> m.minutes() > 90)
                + ", niciun film din 2030? " + movies.stream().noneMatch(m -> m.year() == 2030));

        // reduce: combină totul într-o valoare
        int totalMinutes = movies.stream().map(Movie::minutes).reduce(0, Integer::sum);
        System.out.println("reduce: " + totalMinutes + " minute în total");

        // min / max / findFirst → Optional (stream-ul poate fi gol)
        Optional<Movie> oldest = movies.stream().min(Comparator.comparingInt(Movie::year));
        Optional<Movie> horror = movies.stream().filter(m -> m.genre().equals("Horror")).findFirst();
        System.out.println("cel mai vechi: " + oldest.map(Movie::title).orElse("-") + ", horror: " + horror);

        // collect: joining
        String sf = movies.stream().filter(m -> m.genre().equals("SF")).map(Movie::title)
                .collect(Collectors.joining(", ", "SF: [", "]"));
        System.out.println(sf);

        // groupingBy: gen → listă / număr / medie
        Map<String, Long> perGenre = movies.stream()
                .collect(Collectors.groupingBy(Movie::genre, TreeMap::new, Collectors.counting()));
        System.out.println("câte pe gen:  " + perGenre);

        Map<String, List<String>> titlesPerGenre = movies.stream()
                .collect(Collectors.groupingBy(Movie::genre, TreeMap::new,
                        Collectors.mapping(Movie::title, Collectors.toList())));
        System.out.println("titluri pe gen: " + titlesPerGenre.get("Thriller"));

        Map<String, Double> avg = movies.stream()
                .collect(Collectors.groupingBy(Movie::genre, TreeMap::new, Collectors.averagingDouble(Movie::rating)));
        System.out.printf("media pe gen: Animație=%.2f, SF=%.2f%n", avg.get("Animație"), avg.get("SF"));

        // partitioningBy: exact două grupe, true / false
        Map<Boolean, Long> modern = movies.stream()
                .collect(Collectors.partitioningBy(m -> m.year() >= 2000, Collectors.counting()));
        System.out.println("după 2000: " + modern);

        // toMap: atenție la chei duplicate
        try {
            movies.stream().collect(Collectors.toMap(Movie::year, Movie::title));
        } catch (IllegalStateException e) {
            System.out.println("toMap(year, title) → IllegalStateException: " + e.getMessage());
        }
        Map<Integer, String> byYear = movies.stream()
                .collect(Collectors.toMap(Movie::year, Movie::title, (a, b) -> a + " + " + b, TreeMap::new));
        System.out.println("toMap cu merge: 2010 → " + byYear.get(2010));

        // DEMO EROARE: decomentează → UnsupportedOperationException la runtime: toList() întoarce o listă imutabilă
        // movies.stream().map(Movie::title).toList().add("Dune");
    }
}
