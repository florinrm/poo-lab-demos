package lab11.d03_styles;

import lab11.data.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Slide 3 — Aceeași întrebare, două stiluri:
 * "titlurile filmelor de după 2000 cu nota cel puțin 8.5, în ordine alfabetică, cu majuscule".
 */
public class Demo03Styles {
    public static void main(String[] args) {
        List<Movie> movies = Movie.catalog();

        // Imperativ: CUM — variabile temporare, bucle, if-uri, stare mutabilă
        List<String> result = new ArrayList<>();
        for (Movie m : movies) {
            if (m.year() > 2000 && m.rating() >= 8.5) {
                result.add(m.title().toUpperCase());
            }
        }
        Collections.sort(result);
        System.out.println("imperativ:  " + result);

        // Funcțional: CE — un lanț de transformări, fără variabile intermediare
        List<String> result2 = movies.stream()
                .filter(m -> m.year() > 2000)
                .filter(m -> m.rating() >= 8.5)
                .map(m -> m.title().toUpperCase())
                .sorted()
                .toList();
        System.out.println("funcțional: " + result2);

        // Sursa NU a fost modificată
        System.out.println("catalogul are tot " + movies.size() + " filme");

        // Un al doilea exemplu: cel mai lung film
        Movie longest = movies.stream().max(Comparator.comparingInt(Movie::minutes)).orElseThrow();
        System.out.println("cel mai lung: " + longest.title() + " (" + longest.minutes() + " min)");

        // DEMO EROARE: decomentează → incompatible types: no instance(s) of type variable(s) R exist so that Stream<R> conforms to List<String> (lipsește operația terminală)
        // List<String> titles = movies.stream().map(Movie::title);
    }
}
