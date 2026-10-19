package lab11.d13_optional;

import lab11.data.Movie;

import java.util.List;
import java.util.Optional;

/**
 * Slide 13 — [Nice to know] Optional: o valoare care poate lipsi.
 */
public class Demo13Optional {

    static Optional<Movie> findByTitle(List<Movie> movies, String title) {
        return movies.stream().filter(m -> m.title().equalsIgnoreCase(title)).findFirst();
    }

    static String computeDefault() {
        System.out.println("   (computeDefault apelat)");
        return "necunoscut";
    }

    public static void main(String[] args) {
        List<Movie> movies = Movie.catalog();
        Optional<Movie> up = findByTitle(movies, "up");
        Optional<Movie> dune = findByTitle(movies, "Dune");
        System.out.println(up.map(Movie::title) + " " + dune);

        // ifPresent / ifPresentOrElse
        up.ifPresent(m -> System.out.println("găsit: " + m.title() + " (" + m.year() + ")"));
        dune.ifPresentOrElse(m -> System.out.println("găsit"), () -> System.out.println("Dune nu e în catalog"));

        // map / filter pe Optional: lanț fără if-uri pe null
        String genre = dune.map(Movie::genre).orElse("—");
        boolean upIsLong = up.filter(m -> m.minutes() > 120).isPresent();
        System.out.println("gen Dune: " + genre + ", Up e lung? " + upIsLong);

        // orElse vs orElseGet: argumentul lui orElse se evaluează ÎNTOTDEAUNA
        System.out.println("orElse pe Optional plin:");
        String a = up.map(Movie::title).orElse(computeDefault());
        System.out.println("   → " + a);
        System.out.println("orElseGet pe Optional plin:");
        String b = up.map(Movie::title).orElseGet(Demo13Optional::computeDefault);
        System.out.println("   → " + b);

        // orElseThrow: lipsa e o eroare de logică
        try {
            dune.orElseThrow(() -> new IllegalStateException("filmul trebuia să existe"));
        } catch (IllegalStateException e) {
            System.out.println("orElseThrow → " + e.getMessage());
        }

        // of vs ofNullable
        System.out.println("ofNullable(null) = " + Optional.ofNullable(null));
        try {
            Optional.of(null);
        } catch (NullPointerException e) {
            System.out.println("Optional.of(null) → NullPointerException");
        }

        // DEMO EROARE: decomentează → NoSuchElementException la runtime: get() pe un Optional gol
        // System.out.println(dune.get());
    }
}
