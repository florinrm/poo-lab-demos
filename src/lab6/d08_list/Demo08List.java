package lab6.d08_list;

import java.util.*;

/**
 * Slide 8 — List: index, remove(int) vs remove(Object), ArrayList vs LinkedList.
 */
public class Demo08List {
    public static void main(String[] args) {
        List<String> fruits = new ArrayList<>(List.of("Apple", "Banana"));
        fruits.add("Apple");             // duplicat permis
        fruits.add(1, "Kiwi");           // inserare la index
        String old = fruits.set(0, "Pear");
        System.out.println(fruits + " (set a înlocuit " + old + ")");
        System.out.println("get(2) = " + fruits.get(2) + ", indexOf(Apple) = " + fruits.indexOf("Apple"));

        // Java 21: SequencedCollection → addFirst/getLast/removeLast și pe List
        fruits.addFirst("Plum");
        System.out.println("addFirst: " + fruits + ", getLast: " + fruits.getLast());

        // Capcana: remove(int) vs remove(Object) pe List<Integer>
        List<Integer> nums = new ArrayList<>(List.of(10, 20, 30));
        nums.remove(1);                       // index 1 → scoate 20
        System.out.println("remove(1):                " + nums);
        nums.remove(Integer.valueOf(10));     // obiectul 10
        System.out.println("remove(Integer.valueOf):  " + nums);

        // ArrayList vs LinkedList: acces prin index
        int n = 50_000;
        List<Integer> array = new ArrayList<>();
        List<Integer> linked = new LinkedList<>();
        for (int i = 0; i < n; i++) { array.add(i); linked.add(i); }
        System.out.printf("get(i) pe toată lista: ArrayList %d ms, LinkedList %d ms%n",
                timeGets(array), timeGets(linked));

        // ...și inserare la început
        System.out.printf("add(0, x) de 50.000 ori: ArrayList %d ms, LinkedList %d ms%n",
                timeAddFront(new ArrayList<>()), timeAddFront(new LinkedList<>()));

        // DEMO EROARE: decomentează → unexpected type: required reference, found int
        // List<int> primitives = new ArrayList<>();
    }

    static long timeGets(List<Integer> list) {
        long t = System.nanoTime();
        long sum = 0;
        for (int i = 0; i < list.size(); i++) sum += list.get(i);
        if (sum < 0) System.out.println();     // ca JIT-ul să nu elimine bucla
        return (System.nanoTime() - t) / 1_000_000;
    }

    static long timeAddFront(List<Integer> list) {
        long t = System.nanoTime();
        for (int i = 0; i < 50_000; i++) list.add(0, i);
        return (System.nanoTime() - t) / 1_000_000;
    }
}
