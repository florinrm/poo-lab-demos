package lab6.d11_immutable;

import java.util.*;

/**
 * Slide 11 — Colecții imutabile vs fixed-size vs mutabile.
 */
public class Demo11Immutable {
    public static void main(String[] args) {
        List<String> of = List.of("Apple", "Banana");
        List<String> asList = Arrays.asList("Apple", "Banana");
        List<String> copy = new ArrayList<>(List.of("Apple", "Banana"));

        try { of.add("Kiwi"); } catch (UnsupportedOperationException e) { System.out.println("List.of.add      → UnsupportedOperationException"); }
        try { of.set(0, "Kiwi"); } catch (UnsupportedOperationException e) { System.out.println("List.of.set      → UnsupportedOperationException"); }

        asList.set(0, "Kiwi");
        System.out.println("Arrays.asList.set → merge: " + asList);
        try { asList.add("Plum"); } catch (UnsupportedOperationException e) { System.out.println("Arrays.asList.add → UnsupportedOperationException"); }

        copy.add("Kiwi");
        System.out.println("new ArrayList<>(List.of(...)).add → merge: " + copy);

        // Arrays.asList e o "fereastră" peste tablou
        String[] arr = {"x", "y"};
        List<String> view = Arrays.asList(arr);
        arr[0] = "CHANGED";
        System.out.println("asList vede schimbarea din tablou: " + view);

        // Vedere read-only vs copie imutabilă
        List<String> live = new ArrayList<>(List.of("a"));
        List<String> ro = Collections.unmodifiableList(live);
        List<String> snap = List.copyOf(live);
        live.add("b");
        System.out.println("unmodifiableList: " + ro + ", List.copyOf: " + snap);

        // List.of nu acceptă null
        try { List.of("a", null); } catch (NullPointerException e) { System.out.println("List.of(null) → NullPointerException"); }

        // Map.of / Set.of: aceleași reguli; Set.of cu duplicate → IllegalArgumentException
        try { Set.of("a", "a"); } catch (IllegalArgumentException e) { System.out.println("Set.of(\"a\", \"a\") → IllegalArgumentException"); }

        // DEMO EROARE: decomentează → compilează, dar aruncă UnsupportedOperationException la runtime
        // Collections.sort(List.of(3, 1, 2));
    }
}
