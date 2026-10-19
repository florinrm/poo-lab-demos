package lab11.d05_capture;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Slide 5 — Ce poate "vedea" o lambda: variabile effectively final și this.
 */
public class Demo05Capture {

    private final String name = "Demo05";

    void showThis() {
        Runnable lambda = () -> System.out.println("   în lambda,  this = " + this.getClass().getSimpleName());
        Runnable anon = new Runnable() {
            @Override
            public void run() {
                System.out.println("   în anonimă, this = " + this.getClass().getName()
                        + "  (accesăm exteriorul cu Demo05Capture.this.name = " + Demo05Capture.this.name + ")");
            }
        };
        lambda.run();
        anon.run();
    }

    static Supplier<String> greeter(String who) {
        String prefix = "Salut, ";              // effectively final: nu mai e modificată
        return () -> prefix + who + "!";        // lambda "ține minte" valorile după ce metoda s-a terminat
    }

    public static void main(String[] args) {
        Supplier<String> g = greeter("Ana");
        System.out.println(g.get());

        // Capturăm valoarea, nu variabila: aici nu există nicio variabilă "count" care să fie modificată
        List<String> words = List.of("lambda", "stream", "optional", "map");
        int minLen = 6;
        long longWords = words.stream().filter(w -> w.length() >= minLen).count();
        System.out.println("cuvinte cu cel puțin " + minLen + " litere: " + longWords);

        // "Ocolirea" cu un obiect mutabil compilează — dar e exact side effect-ul de evitat
        AtomicInteger counter = new AtomicInteger();
        words.forEach(w -> counter.incrementAndGet());
        System.out.println("counter = " + counter.get() + " (merge, dar count() era varianta corectă)");

        new Demo05Capture().showThis();

        // DEMO EROARE: decomentează → local variables referenced from a lambda expression must be final or effectively final
        // int total = 0; words.forEach(w -> total += w.length());
    }
}
