package lab4.d11_record;

import java.util.ArrayList;
import java.util.List;

/**
 * Slide 17: record.
 *
 *  - generat automat: constructor canonic, accesori name()/age(), equals, hashCode, toString;
 *  - constructor compact pentru validare / normalizare;
 *  - metode proprii si implementare de interfete;
 *  - NU are setteri; NU poate extinde clase (extinde deja java.lang.Record);
 *  - imutabilitatea e SUPERFICIALA: o componenta List poate fi modificata din afara.
 */
record Person(String name, int age) {
}

record Product(String name, double price) {
    Product { // constructor compact: fara lista de parametri, fara this.x = x
        if (price < 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        name = name.strip(); // poti normaliza parametrii inainte de atribuirea automata
    }
}

record Point(int x, int y) {
    public double distanceToOrigin() {
        return Math.sqrt(x * x + y * y);
    }

    public static final Point ORIGIN = new Point(0, 0); // campuri static: permise
    // DEMO EROARE: camp de instanta suplimentar
    // private int z;
}

interface Printable {
    void print();
}

record Book(String title, String author) implements Printable {
    @Override
    public void print() {
        System.out.println("  " + title + " by " + author);
    }
}

record Playlist(String name, List<String> songs) {
}

record SafePlaylist(String name, List<String> songs) {
    SafePlaylist {
        songs = List.copyOf(songs);
    }
}

// DEMO EROARE: record-urile nu pot extinde clase (extind implicit java.lang.Record)
// -> javac nici nu accepta sintaxa: "'{' expected"
// record Student(String name) extends Object { }

public class Demo11Records {
    public static void main(String[] args) {
        Person p = new Person("Ana", 25);
        Person q = new Person("Ana", 25);
        System.out.println("toString: " + p);
        System.out.println("accesori: " + p.name() + ", " + p.age());
        System.out.println("p.equals(q) = " + p.equals(q) + ", p == q = " + (p == q));
        System.out.println("hashCode egal: " + (p.hashCode() == q.hashCode()));
        // DEMO EROARE: nu exista setteri si campurile sunt final
        // p.age = 26;
        // p.setAge(26);

        System.out.println("\n== Constructor compact ==");
        System.out.println(new Product("  Laptop  ", 3999.99));
        try {
            new Product("Bug", -1);
        } catch (IllegalArgumentException e) {
            System.out.println("new Product(\"Bug\", -1) -> " + e.getMessage());
        }

        System.out.println("\n== Metode si interfete ==");
        System.out.println("Point(3,4).distanceToOrigin() = " + new Point(3, 4).distanceToOrigin());
        new Book("Ion", "Liviu Rebreanu").print();

        System.out.println("\n== Imutabilitate superficiala ==");
        List<String> songs = new ArrayList<>(List.of("Song A"));
        Playlist pl = new Playlist("Mix", songs);
        songs.add("Song B");
        pl.songs().add("Song C");
        System.out.println("Playlist     = " + pl);

        List<String> songs2 = new ArrayList<>(List.of("Song A"));
        SafePlaylist spl = new SafePlaylist("Mix", songs2);
        songs2.add("Song B");
        System.out.println("SafePlaylist = " + spl);
    }
}
