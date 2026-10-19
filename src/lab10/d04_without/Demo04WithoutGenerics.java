package lab10.d04_without;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Slide 4 — Două încercări de a face "o listă doar de date calendaristice" fără generics.
 */
@SuppressWarnings({"rawtypes", "unchecked", "serial"})
public class Demo04WithoutGenerics {

    // Încercarea 1: "suprascriem" add cu un tip mai specific → de fapt e overload
    static class DateList extends ArrayList {
        public boolean add(LocalDate d) {
            System.out.println("   add(LocalDate) apelat");
            return super.add(d);
        }
    }

    // Încercarea 2: o clasă separată care deleagă
    static class DateBox {
        private final ArrayList inner = new ArrayList();
        void add(LocalDate d) { inner.add(d); }
        LocalDate get(int i) { return (LocalDate) inner.get(i); }
        int size() { return inner.size(); }
    }

    static int countAll(List<?> list) { return list.size(); }

    public static void main(String[] args) {
        DateList list = new DateList();
        list.add(LocalDate.of(2026, 10, 19));
        list.add("Hello");                         // alege add(Object) moștenit — merge!
        list.add(42);
        System.out.println("DateList: " + list);

        // Prin referința părintelui, overload-ul nici nu e vizibil
        ArrayList asParent = list;
        asParent.add(LocalDate.of(2027, 1, 25));   // apelează add(Object): fără mesaj
        System.out.println("prin ArrayList: " + list.size() + " elemente");

        DateBox box = new DateBox();
        box.add(LocalDate.of(2026, 12, 25));
        box.add(LocalDate.of(2026, 1, 1));
        System.out.println("DateBox: " + box.get(0) + ", " + box.get(1) + " — tipat, dar nu e List");

        // Cu generics: ambele probleme dispar
        List<LocalDate> dates = new ArrayList<>(List.of(LocalDate.of(2026, 12, 25), LocalDate.of(2026, 1, 1)));
        Collections.sort(dates);
        System.out.println("List<LocalDate> sortată: " + dates + ", countAll = " + countAll(dates));

        // DEMO EROARE: decomentează → no suitable method found for sort(DateBox): DateBox nu e o List
        // Collections.sort(box);
    }
}
