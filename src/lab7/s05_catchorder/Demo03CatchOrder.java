package lab7.s05_catchorder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Slide 5 — mai multe catch, ordinea lor, multi-catch.
 */
public class Demo03CatchOrder {

    static void risky(int kind) throws IOException {
        switch (kind) {
            case 0 -> throw new FileNotFoundException("lipsește config.txt");
            case 1 -> throw new IOException("disc indisponibil");
            case 2 -> throw new IllegalStateException("stare invalidă");
            default -> System.out.println("totul ok");
        }
    }

    public static void main(String[] args) {
        for (int k = 0; k <= 3; k++) {
            try {
                risky(k);
            } catch (FileNotFoundException e) {         // cel mai specific primul
                System.out.println("[FNF]  " + e.getMessage());
            } catch (IOException e) {                   // apoi părintele
                System.out.println("[IO]   " + e.getMessage());
            } catch (IllegalStateException | IllegalArgumentException e) { // multi-catch: tipuri NEînrudite
                System.out.println("[STATE/ARG] " + e.getMessage());
            }
        }

        // DEMO EROARE 1 — decomentează: catch general înaintea celui specific.
        // Nu e "cod care nu rulează niciodată", e EROARE DE COMPILARE:
        // "exception ArithmeticException has already been caught"
        //
        // try { int a = 5 / 0; }
        // catch (Exception e) { }
        // catch (ArithmeticException e) { }

        // DEMO EROARE 2 — decomentează: multi-catch cu tipuri înrudite (exemplul din laborator).
        // "Alternatives in a multi-catch statement cannot be related by subclassing"
        //
        // try { new FileReader("x.txt"); }
        // catch (IOException | FileNotFoundException e) { }
    }
}
