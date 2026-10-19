package lab10.d05_classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Slide 5 — Clase și interfețe generice, plus moștenirea lor.
 */
public class Demo05GenericClasses {

    // Doi parametri de tip
    static class Pair<A, B> {
        private final A first;
        private final B second;

        Pair(A first, B second) { this.first = first; this.second = second; }

        A first() { return first; }
        B second() { return second; }

        // Metodă care întoarce alt tip parametrizat: tipurile își schimbă locul
        Pair<B, A> swap() { return new Pair<>(second, first); }

        // Factory static generic: compilatorul deduce A și B
        static <A, B> Pair<A, B> of(A a, B b) { return new Pair<>(a, b); }

        @Override
        public String toString() { return "(" + first + ", " + second + ")"; }
    }

    // Interfață generică
    interface Converter<T, R> {
        R convert(T input);
    }

    // Moștenire: păstrăm un parametru, îl fixăm pe celălalt
    static class Named<V> extends Pair<String, V> {
        Named(String name, V value) { super(name, value); }
    }

    // Moștenire: fixăm tot → clasa nu mai e generică
    static class Score extends Pair<String, Integer> {
        Score(String who, int points) { super(who, points); }
    }

    // Un container generic simplu
    static class Shelf<T> {
        private final List<T> items = new ArrayList<>();
        void put(T item) { items.add(item); }
        T takeLast() { return items.remove(items.size() - 1); }
        int size() { return items.size(); }
    }

    public static void main(String[] args) {
        Pair<String, Integer> p = new Pair<>("Ana", 10);
        String name = p.first();             // fără cast
        int grade = p.second();              // unboxing automat
        System.out.println(p + " → " + name + " are " + grade + "; swap: " + p.swap());

        var q = Pair.of(3.14, true);         // Pair<Double, Boolean>, dedus
        System.out.println("Pair.of: " + q + ", second e " + q.second().getClass().getSimpleName());

        Converter<String, Integer> length = s -> s.length();
        Converter<Integer, String> stars = n -> "*".repeat(n);
        System.out.println("convert: " + length.convert("generics") + " " + stars.convert(5));

        Named<Double> pi = new Named<>("pi", 3.14159);
        Score s = new Score("Ion", 95);
        System.out.println("Named: " + pi + ", Score: " + s + " (Score nu mai are parametri de tip)");

        Shelf<String> books = new Shelf<>();
        books.put("Ion");
        books.put("Baltagul");
        System.out.println("Shelf: ultima carte = " + books.takeLast() + ", rămân " + books.size());

        // DEMO EROARE: decomentează → unexpected type: required reference, found int
        // Pair<int, String> bad = new Pair<>(1, "x");
    }
}
