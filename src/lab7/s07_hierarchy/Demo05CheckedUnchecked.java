package lab7.s07_hierarchy;

import java.io.FileReader;
import java.io.IOException;

/**
 * Slide 7 — Throwable / Error / Exception / RuntimeException.
 * "Checked" = compilatorul te obligă să tratezi sau să declari (throws).
 * Orice excepție poate fi prinsă; diferența e doar la compilare.
 */
public class Demo05CheckedUnchecked {

    // checked: trebuie declarată în semnătură
    static void openConfig() throws IOException {
        new FileReader("nu-exista.cfg").close();
    }

    // unchecked: nu trebuie declarată
    static int firstChar(String s) {
        return s.charAt(0);   // NPE dacă s e null, StringIndexOutOfBounds dacă e gol
    }

    public static void main(String[] args) {
        for (Throwable t : new Throwable[]{
                new java.io.FileNotFoundException(), new IllegalArgumentException(),
                new StackOverflowError(), new Exception(), new Throwable()}) {
            System.out.printf("%-34s checked? %s%n", t.getClass().getSimpleName(), isChecked(t));
        }
        System.out.println();

        try {
            openConfig();
        } catch (IOException e) {
            System.out.println("checked, tratată: " + e);
        }

        try {
            firstChar("");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("unchecked — se poate prinde, dar bug-ul trebuie reparat: " + e.getMessage());
        }

        // Error: se poate prinde tehnic, dar de obicei nu ai ce face după.
        try {
            recurse(0);
        } catch (StackOverflowError e) {
            System.out.println("StackOverflowError prins (doar ca demonstrație)");
        }

        // DEMO EROARE — decomentează: apel la metodă checked fără try/catch și fără throws.
        // "unreported exception java.io.IOException; must be caught or declared to be thrown"
        //
        // openConfig();
    }

    static boolean isChecked(Throwable t) {
        return !(t instanceof RuntimeException || t instanceof Error);
    }

    static int recurse(int n) {
        return recurse(n + 1);
    }
}
