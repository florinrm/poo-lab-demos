package lab8.s16_visitor;

import java.util.List;

/**
 * Slide 16 — Visitor complet.
 * 1. vizitator cu stare (adună durata totală);
 * 2. operație nouă = clasă nouă, elementele rămân neatinse;
 * 3. tip nou de element = toți vizitatorii trebuie modificați (DEMO EROARE).
 */
public class Demo12Visitor {

    interface Media { void accept(MediaVisitor v); }

    interface MediaVisitor {
        void visit(Song s);
        void visit(Podcast p);
        void visit(Movie m);
        // DEMO EROARE, pasul 2: decomentează -> fiecare vizitator „is not abstract and does not override visit(Audiobook)"
        // void visit(Audiobook a);
    }

    record Song(String title, int seconds) implements Media {
        public void accept(MediaVisitor v) { v.visit(this); }
    }
    record Podcast(String title, int episodes, int minutesEach) implements Media {
        public void accept(MediaVisitor v) { v.visit(this); }
    }
    record Movie(String title, int minutes) implements Media {
        public void accept(MediaVisitor v) { v.visit(this); }
    }
    // DEMO EROARE, pasul 1: decomentează -> "no suitable method found for visit(Audiobook)": vizitatorul nu știe de tipul nou.
    // record Audiobook(String title, int hours) implements Media {
    //     public void accept(MediaVisitor v) { v.visit(this); }
    // }

    /** Vizitator cu stare: adună pe măsură ce parcurge. */
    static class TotalDurationVisitor implements MediaVisitor {
        private int totalSeconds;
        public void visit(Song s)    { totalSeconds += s.seconds(); }
        public void visit(Podcast p) { totalSeconds += p.episodes() * p.minutesEach() * 60; }
        public void visit(Movie m)   { totalSeconds += m.minutes() * 60; }
        int hours() { return totalSeconds / 3600; }
    }

    /** Operație nouă, adăugată fără să modificăm Song / Podcast / Movie. */
    static class CsvLineVisitor implements MediaVisitor {
        public void visit(Song s)    { System.out.println("song;" + s.title() + ";" + s.seconds()); }
        public void visit(Podcast p) { System.out.println("podcast;" + p.title() + ";" + p.episodes()); }
        public void visit(Movie m)   { System.out.println("movie;" + m.title() + ";" + m.minutes()); }
    }

    public static void main(String[] args) {
        List<Media> library = List.of(
                new Song("Ploaia", 215),
                new Podcast("Cod curat", 40, 45),
                new Movie("Moromeții", 150),
                new Song("Noaptea", 190));

        TotalDurationVisitor total = new TotalDurationVisitor();
        for (Media m : library) m.accept(total);
        System.out.println("Durată totală: ~" + total.hours() + " ore\n");

        CsvLineVisitor csv = new CsvLineVisitor();
        library.forEach(m -> m.accept(csv));

        // Observație: vizitatorii folosesc title(), seconds() ... — datele trebuie să fie
        // accesibile din exterior. Acesta e costul de încapsulare menționat pe pagină.
    }
}
