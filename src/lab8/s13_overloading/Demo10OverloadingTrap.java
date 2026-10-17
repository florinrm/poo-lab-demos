package lab8.s13_overloading;

import java.util.List;

/**
 * Slide 13-14 — de ce nu ajung instanceof și overloading.
 * Overloading se alege la compilare, după tipul REFERINȚEI.
 * Override se alege la rulare, după tipul OBIECTULUI.
 */
public class Demo10OverloadingTrap {

    interface Media { String title(); }
    record Song(String title, int seconds) implements Media { }
    record Podcast(String title, int episodes) implements Media { }
    record Movie(String title, int minutes) implements Media { }

    static class Printer {
        void print(Media m)   { System.out.println("   Media generic: " + m.title()); }
        void print(Song s)    { System.out.println("   Melodie, " + s.seconds() + " s"); }
        void print(Podcast p) { System.out.println("   Podcast, " + p.episodes() + " episoade"); }
        void print(Movie f)   { System.out.println("   Film, " + f.minutes() + " min"); }
    }

    public static void main(String[] args) {
        Printer printer = new Printer();
        List<Media> library = List.of(
                new Song("Ploaia", 215),
                new Podcast("Cod curat", 40),
                new Movie("Moromeții", 150));

        System.out.println("1. Overloading pe o listă de Media:");
        for (Media m : library) {
            printer.print(m);                    // tip static: Media -> print(Media) de fiecare dată
        }

        System.out.println("\n2. Aceleași obiecte, referințe de tipul concret:");
        Song s = new Song("Ploaia", 215);
        printer.print(s);                        // tip static: Song -> print(Song)

        System.out.println("\n3. instanceof — merge, dar fiecare tip nou cere încă o ramură:");
        for (Media m : library) {
            if (m instanceof Song song) printer.print(song);
            else if (m instanceof Podcast p) printer.print(p);
            else if (m instanceof Movie f) printer.print(f);
        }
        // Dacă adăugăm Audiobook, codul de mai sus compilează, dar Audiobook e ignorat fără niciun avertisment.
    }
}
