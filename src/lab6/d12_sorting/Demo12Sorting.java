package lab6.d12_sorting;

import java.util.*;

/**
 * Slide 12 — Comparable (ordine naturală) vs Comparator (ordini alternative).
 */
public class Demo12Sorting {

    static class Book implements Comparable<Book> {
        private final String title;
        private final String author;
        private final int year;

        Book(String title, String author, int year) {
            this.title = title;
            this.author = author;
            this.year = year;
        }

        String getTitle() { return title; }
        String getAuthor() { return author; }
        int getYear() { return year; }

        // Ordinea naturală: după an
        @Override
        public int compareTo(Book o) {
            return Integer.compare(year, o.year);
        }

        @Override
        public String toString() {
            return title + " (" + year + ")";
        }
    }

    // Nu implementează Comparable
    static class Movie {
        final String name;
        Movie(String name) { this.name = name; }
    }

    public static void main(String[] args) {
        List<Book> books = new ArrayList<>(List.of(
                new Book("Ion", "Rebreanu", 1920),
                new Book("Enigma Otiliei", "Calinescu", 1938),
                new Book("Baltagul", "Sadoveanu", 1930),
                new Book("Padurea spanzuratilor", "Rebreanu", 1922)));

        Collections.sort(books);                       // compareTo
        System.out.println("natural (an):  " + books);

        // Comparator ca clasă anonimă
        books.sort(new Comparator<Book>() {
            @Override
            public int compare(Book a, Book b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
        System.out.println("după titlu:    " + books);

        // Aceeași idee, scris compact (lambda / Comparator.comparing)
        books.sort(Comparator.comparing(Book::getAuthor).thenComparing(Book::getYear));
        System.out.println("autor, apoi an: " + books);

        books.sort(Comparator.reverseOrder());
        System.out.println("an descrescător: " + books);

        // Capcana: comparator prin scădere → overflow
        List<Integer> nums = new ArrayList<>(List.of(5, Integer.MIN_VALUE, 13, 7));
        nums.sort((a, b) -> b - a);
        System.out.println("b - a:              " + nums + "   ← greșit");
        nums.sort((a, b) -> Integer.compare(b, a));
        System.out.println("Integer.compare:    " + nums);

        // Capcana 2: cu double, scăderea + cast la int pierde diferențele mici
        System.out.println("(int)(8.5 - 8.4) = " + (int) (8.5 - 8.4) + "  vs  Double.compare = " + Double.compare(8.5, 8.4));

        // Movie nu are ordine naturală, dar putem da una din exterior
        List<Movie> movies = new ArrayList<>(List.of(new Movie("Up"), new Movie("Alien")));
        movies.sort(Comparator.comparing(m -> m.name));
        System.out.println("filme: " + movies.get(0).name + ", " + movies.get(1).name);

        // DEMO EROARE: decomentează → no suitable method found for sort(List<Movie>)
        // Collections.sort(movies);
    }
}
