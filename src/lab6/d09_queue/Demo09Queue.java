package lab6.d09_queue;

import java.util.*;

/**
 * Slide 9 — Queue (FIFO), Deque ca stivă (LIFO), PriorityQueue.
 */
public class Demo09Queue {
    public static void main(String[] args) {
        // FIFO: coadă la imprimantă
        Queue<String> printer = new ArrayDeque<>();
        printer.offer("curs.pdf");
        printer.offer("tema.pdf");
        printer.offer("poza.png");
        System.out.println("peek: " + printer.peek() + ", poll: " + printer.poll() + ", rămân: " + printer);

        // Coadă goală: poll/peek → null, remove/element → excepție
        Queue<String> empty = new ArrayDeque<>();
        System.out.println("poll pe goală: " + empty.poll());
        try {
            empty.remove();
        } catch (NoSuchElementException e) {
            System.out.println("remove pe goală: NoSuchElementException");
        }

        // LIFO: istoric "Back" în browser
        Deque<String> history = new ArrayDeque<>();
        history.push("google.com");
        history.push("ocw.cs.pub.ro");
        history.push("docs.oracle.com");
        System.out.println("back → " + history.pop() + ", stiva: " + history);

        // PriorityQueue: poll scoate mereu minimul...
        Queue<Integer> pq = new PriorityQueue<>(List.of(5, 1, 3, 2, 4));
        System.out.println("println(pq): " + pq + "   ← ordinea heap-ului, NU sortat");
        StringBuilder order = new StringBuilder();
        while (!pq.isEmpty()) order.append(pq.poll()).append(' ');
        System.out.println("poll în buclă: " + order);

        // ...sau maximul, cu un Comparator
        Queue<Integer> maxFirst = new PriorityQueue<>(Comparator.reverseOrder());
        maxFirst.addAll(List.of(5, 1, 3));
        System.out.println("max-heap poll: " + maxFirst.poll());

        // ArrayDeque nu acceptă null
        try {
            new ArrayDeque<String>().offer(null);
        } catch (NullPointerException e) {
            System.out.println("ArrayDeque.offer(null): NullPointerException");
        }

        // DEMO EROARE: decomentează → Queue is abstract; cannot be instantiated
        // Queue<String> q = new Queue<>();
    }
}
