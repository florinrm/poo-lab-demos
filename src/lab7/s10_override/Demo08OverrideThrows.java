package lab7.s10_override;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Slide 10 — excepții și suprascriere.
 * Copilul poate arunca: aceleași, mai specifice sau niciuna. Niciodată checked noi/mai largi.
 * Motivul: codul scris pentru părinte trebuie să rămână corect cu orice copil (upcast).
 */
public class Demo08OverrideThrows {

    static class Exporter {
        void export(String data) throws IOException {
            System.out.println("Exporter generic: " + data);
        }
    }

    static class CsvExporter extends Exporter {
        @Override
        void export(String data) throws FileNotFoundException {   // OK: mai specific
            System.out.println("CSV: " + data.replace(' ', ','));
        }
    }

    static class ConsoleExporter extends Exporter {
        @Override
        void export(String data) {                                 // OK: nicio excepție
            System.out.println("Consolă: " + data);
        }
    }

    static class StrictExporter extends Exporter {
        @Override
        void export(String data) {                                 // OK: unchecked e voie oricând
            if (data.isBlank()) throw new IllegalArgumentException("nimic de exportat");
            System.out.println("Strict: " + data);
        }
    }

    // DEMO EROARE — decomentează: checked mai largă decât a părintelui.
    // "overridden method does not throw java.lang.Exception"
    //
    // static class BrokenExporter extends Exporter {
    //     @Override
    //     void export(String data) throws Exception { }
    // }

    public static void main(String[] args) {
        Exporter[] all = {new Exporter(), new CsvExporter(), new ConsoleExporter(), new StrictExporter()};
        for (Exporter e : all) {
            try {
                e.export("a b c");          // apelantul știe doar de IOException — și ajunge
            } catch (IOException ex) {
                System.out.println("eroare I/O: " + ex.getMessage());
            }
        }

        // Prin tipul copilului, compilatorul nu mai cere try/catch:
        new ConsoleExporter().export("fără try");
    }
}
