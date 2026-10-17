package lab8.s03_problem;

import java.util.HashMap;
import java.util.Map;

/**
 * Slide 3 — problema pe care o rezolvă Singleton.
 * Trei componente își fac fiecare propriul obiect de setări:
 * încărcarea costisitoare se repetă, iar dacă „fișierul" se schimbă între timp,
 * componentele ajung să lucreze cu valori diferite.
 */
public class Demo01ConfigProblem {

    /** Simulează fișierul de pe disc; îl putem modifica între încărcări. */
    static final Map<String, String> DISK = new HashMap<>(Map.of(
            "ui.language", "ro",
            "cache.size", "64",
            "export.format", "csv"));

    static class AppSettings {
        static int loads = 0;
        private final Map<String, String> values;

        AppSettings() {
            loads++;
            System.out.println("   ... citesc setările de pe disc (încărcarea #" + loads + ")");
            values = new HashMap<>(DISK);          // copie: ce era pe disc ÎN ACEST MOMENT
        }

        String get(String key) { return values.get(key); }
    }

    static class MenuBar {
        AppSettings s = new AppSettings();
        void show() { System.out.println("Meniu în limba: " + s.get("ui.language")); }
    }

    static class SearchPanel {
        AppSettings s = new AppSettings();
        void show() { System.out.println("Căutare în limba: " + s.get("ui.language")); }
    }

    static class Exporter {
        AppSettings s = new AppSettings();
        void show() { System.out.println("Export ca: " + s.get("export.format")); }
    }

    public static void main(String[] args) {
        MenuBar menu = new MenuBar();
        DISK.put("ui.language", "en");             // cineva schimbă fișierul între timp
        SearchPanel search = new SearchPanel();
        Exporter exporter = new Exporter();

        menu.show();
        search.show();
        exporter.show();

        System.out.println("\nÎncărcări: " + AppSettings.loads + " (am fi vrut una)");
        System.out.println("Meniul și căutarea nu mai sunt de acord asupra limbii.");
    }
}
