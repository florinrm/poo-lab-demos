package lab6.d10_map;

import java.util.*;

/**
 * Slide 10 — Map: put/get, parcurgere, cele trei implementări, TreeMap navigabil.
 */
public class Demo10Map {
    public static void main(String[] args) {
        Map<String, Integer> stock = new HashMap<>();
        System.out.println("put nou întoarce: " + stock.put("Apple", 10));
        System.out.println("put peste întoarce vechea valoare: " + stock.put("Apple", 40));
        stock.put("Banana", 20);
        stock.put("Cherry", 30);
        System.out.println("get(Kiwi) = " + stock.get("Kiwi")
                + ", getOrDefault = " + stock.getOrDefault("Kiwi", 0)
                + ", containsKey(Apple) = " + stock.containsKey("Apple"));

        // Parcurgere: entrySet e varianta corectă când ai nevoie de cheie + valoare
        for (Map.Entry<String, Integer> e : stock.entrySet()) {
            System.out.println("  " + e.getKey() + " -> " + e.getValue());
        }

        // Numărare de apariții: merge
        Map<String, Integer> count = new TreeMap<>();
        for (String w : "a fost odata ca niciodata a fost".split(" ")) {
            count.merge(w, 1, Integer::sum);
        }
        System.out.println("frecvențe: " + count);

        // Aceleași chei, trei ordini
        List<String> keys = List.of("Cherry", "Apple", "Banana", "Date");
        Map<String, Integer> hash = new HashMap<>(), linked = new LinkedHashMap<>(), tree = new TreeMap<>();
        for (String k : keys) { hash.put(k, k.length()); linked.put(k, k.length()); tree.put(k, k.length()); }
        System.out.println("HashMap:       " + hash);
        System.out.println("LinkedHashMap: " + linked);
        System.out.println("TreeMap:       " + tree);

        // TreeMap = NavigableMap
        TreeMap<Integer, String> bands = new TreeMap<>(Map.of(0, "picat", 5, "trecut", 9, "excelent"));
        System.out.println("nota 7 → " + bands.floorEntry(7).getValue());
        System.out.println("subMap(0, 9) = " + bands.subMap(0, 9) + "  (9 exclus)");

        // Capcană: cheia Long, căutare cu int → autoboxing la Integer → nu se găsește
        Map<Long, String> users = new HashMap<>();
        users.put(1L, "admin");
        System.out.println("users.get(1) = " + users.get(1) + ", users.get(1L) = " + users.get(1L));

        // DEMO EROARE: decomentează → Map<int, ...>: primitive nu pot fi argumente de tip
        // Map<int, String> m = new HashMap<>();
    }
}
