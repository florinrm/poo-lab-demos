package lab11.d07_methodrefs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Slide 7 — Method references: patru forme, fiecare cu lambda echivalentă.
 */
public class Demo07MethodRefs {

    static boolean isPalindrome(String s) {
        return new StringBuilder(s).reverse().toString().equalsIgnoreCase(s);
    }

    public static void main(String[] args) {
        // 1. Metodă statică:  Clasa::metodaStatica
        Function<String, Integer> parse = Integer::parseInt;              // s -> Integer.parseInt(s)
        Function<String, Boolean> pal = Demo07MethodRefs::isPalindrome;   // s -> isPalindrome(s)
        System.out.println("1. " + parse.apply("42") + " " + pal.apply("Cojoc"));

        // 2. Metoda unui obiect anume:  obiect::metoda
        String course = "Programare Orientată pe Obiecte";
        Function<String, Boolean> inCourse = course::contains;            // s -> course.contains(s)
        Consumer<Object> out = System.out::println;                       // x -> System.out.println(x)
        out.accept("2. " + inCourse.apply("Obiecte"));

        // 3. Metoda unui obiect oarecare de un tip:  Tip::metoda — primul parametru devine "this"
        Function<String, String> upper = String::toUpperCase;             // s -> s.toUpperCase()
        BiFunction<String, String, Boolean> starts = String::startsWith;  // (s, p) -> s.startsWith(p)
        Comparator<String> ci = String::compareToIgnoreCase;              // (a, b) -> a.compareToIgnoreCase(b)
        List<String> names = new ArrayList<>(List.of("ion", "Ana", "maria", "Bogdan"));
        names.sort(ci);
        System.out.println("3. " + upper.apply("java") + " " + starts.apply("lambda", "lam") + " " + names);

        // 4. Constructor:  Clasa::new
        Supplier<List<String>> newList = ArrayList::new;                  // () -> new ArrayList<>()
        Function<String, StringBuilder> sb = StringBuilder::new;          // s -> new StringBuilder(s)
        List<String> fresh = newList.get();
        fresh.add("x");
        System.out.println("4. " + fresh + " " + sb.apply("abc").reverse());

        // Același nume, interpretat după interfața țintă
        Function<String, Integer> length1 = String::length;
        System.out.println("String::length ca Function: " + length1.apply("stream"));

        // DEMO EROARE: decomentează → incompatible types: invalid method reference (parseInt are nevoie de un argument)
        // Supplier<Integer> nope = Integer::parseInt;
    }
}
