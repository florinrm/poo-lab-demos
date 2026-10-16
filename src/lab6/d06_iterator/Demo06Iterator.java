package lab6.d06_iterator;

import java.util.*;

/**
 * Slide 6 — Iterator, for-each și ConcurrentModificationException.
 */
public class Demo06Iterator {
    public static void main(String[] args) {
        List<Double> temps = new ArrayList<>(List.of(3.5, 7.0, 1.2, 9.9, 4.8));

        // 1. Ștergere sigură cu iteratorul
        Iterator<Double> it = temps.iterator();
        while (it.hasNext()) {
            Double t = it.next();       // next() ÎNAINTE de remove()
            if (t < 5.0) it.remove();
        }
        System.out.println("după iterator.remove(): " + temps);

        // 2. Varianta scurtă (Java 8+)
        List<Double> temps2 = new ArrayList<>(List.of(3.5, 7.0, 1.2, 9.9, 4.8));
        temps2.removeIf(t -> t < 5.0);
        System.out.println("după removeIf:          " + temps2);

        // 3. Ștergere din colecție în for-each → excepție
        List<String> names = new ArrayList<>(List.of("Ana", "Ion", "Maria", "Dan"));
        try {
            for (String n : names) {
                if (n.startsWith("A")) names.remove(n);
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("for-each + remove: ConcurrentModificationException");
        }

        // 4. ...dar nu întotdeauna! Penultimul element: bucla se oprește înainte de verificare
        List<String> sneaky = new ArrayList<>(List.of("Ana", "Ion", "Maria"));
        for (String n : sneaky) {
            if (n.equals("Ion")) sneaky.remove(n);
        }
        System.out.println("fără excepție, dar Maria nu a fost vizitată: " + sneaky);

        // 5. it.remove() de două ori la rând → IllegalStateException
        Iterator<String> it2 = new ArrayList<>(List.of("x", "y")).iterator();
        it2.next();
        it2.remove();
        try {
            it2.remove();
        } catch (IllegalStateException e) {
            System.out.println("remove() fără next() între ele: IllegalStateException");
        }

        // DEMO EROARE: decomentează → for-each not applicable to expression type (Map nu e Iterable)
        // for (Object o : new HashMap<String, Integer>()) { }
    }
}
