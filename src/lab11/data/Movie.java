package lab11.data;

import java.util.List;

/**
 * Date comune pentru toate demo-urile din Lab 11.
 */
public record Movie(String title, String genre, int year, double rating, int minutes, List<String> actors) {

    public static List<Movie> catalog() {
        return List.of(
                new Movie("Inception", "SF", 2010, 8.8, 148, List.of("DiCaprio", "Page", "Hardy")),
                new Movie("Interstellar", "SF", 2014, 8.7, 169, List.of("McConaughey", "Hathaway")),
                new Movie("The Matrix", "SF", 1999, 8.7, 136, List.of("Reeves", "Moss")),
                new Movie("Moromeții", "Dramă", 1987, 8.4, 140, List.of("Rebengiuc")),
                new Movie("4 luni, 3 săptămâni și 2 zile", "Dramă", 2007, 7.9, 113, List.of("Marinca", "Vasiliu")),
                new Movie("Parasite", "Thriller", 2019, 8.5, 132, List.of("Song", "Choi")),
                new Movie("Se7en", "Thriller", 1995, 8.6, 127, List.of("Pitt", "Freeman")),
                new Movie("Shutter Island", "Thriller", 2010, 8.2, 138, List.of("DiCaprio", "Ruffalo")),
                new Movie("Up", "Animație", 2009, 8.3, 96, List.of("Asner")),
                new Movie("Cars 2", "Animație", 2011, 6.2, 106, List.of("Wilson")));
    }
}
