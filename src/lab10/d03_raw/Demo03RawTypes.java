package lab10.d03_raw;

import java.util.ArrayList;
import java.util.List;

/**
 * Slide 3 — Raw types: eroarea apare la runtime, departe de locul unde a fost cauzată.
 */
@SuppressWarnings({"rawtypes", "unchecked"})   // scoateți adnotarea și compilați cu -Xlint ca să vedeți warning-urile
public class Demo03RawTypes {

    // Undeva, departe, cineva adaugă "ce are la îndemână"
    static void loadGrades(List grades) {
        grades.add(9);
        grades.add(10);
        grades.add("absent");          // compilatorul nu are cum să ne oprească
    }

    public static void main(String[] args) {
        List raw = new ArrayList();
        loadGrades(raw);

        int sum = 0;
        try {
            for (Object o : raw) {
                sum += (Integer) o;    // cast manual la fiecare citire
            }
        } catch (ClassCastException e) {
            System.out.println("raw: ClassCastException la citire, după sum=" + sum);
        }

        // Aceeași listă "îmbrăcată" generic: compilează cu un warning unchecked...
        List<Integer> typed = raw;
        System.out.println("typed.size() = " + typed.size() + "  ← nicio eroare încă");
        try {
            Integer last = typed.get(2);   // ...și explodează abia aici (heap pollution)
            System.out.println(last);
        } catch (ClassCastException e) {
            System.out.println("typed.get(2): ClassCastException — cast-ul l-a pus compilatorul, invizibil");
        }

        // Cu generics de la început: eroarea e la compilare, unde îi e locul
        List<Integer> safe = new ArrayList<>();
        safe.add(9);
        safe.add(10);
        int total = 0;
        for (int g : safe) total += g;     // fără cast
        System.out.println("safe: total = " + total);

        // DEMO EROARE: decomentează → incompatible types: String cannot be converted to Integer
        // safe.add("absent");
    }
}
