package lab3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * Slide 8 — „preferă compunerea în locul moștenirii”.
 *
 * java.util.Stack extinde Vector (moștenire), deci expune TOATE metodele unei liste.
 * O stivă construită prin compunere expune doar ce trebuie.
 */
public class Demo06CompositionVsInheritance {

    /** Stivă prin compunere: are o colecție înăuntru, expune doar push/pop/peek. */
    static class SafeStack<T> {
        private final Deque<T> items = new ArrayDeque<>();

        void push(T item) { items.push(item); }
        T pop()           { return items.pop(); }
        T peek()          { return items.peek(); }
        boolean isEmpty() { return items.isEmpty(); }

        @Override public String toString() { return items.toString(); }
    }

    public static void main(String[] args) {
        System.out.println("== java.util.Stack (Stack extends Vector) ==");
        Stack<String> stack = new Stack<>();
        stack.push("A");
        stack.push("B");
        stack.push("C");

        stack.add(0, "INTRUS");   // moștenit din Vector: inserăm la baza stivei
        stack.remove("B");        // ... și scoatem din mijloc
        System.out.println("  conținut: " + stack);
        System.out.println("  get(0) = " + stack.get(0) + "  <- acces direct, nu prin vârf");

        System.out.println();
        System.out.println("== SafeStack (compunere) ==");
        SafeStack<String> safe = new SafeStack<>();
        safe.push("A");
        safe.push("B");
        safe.push("C");
        System.out.println("  pop() = " + safe.pop() + ", rămâne " + safe);
        // safe.add(0, "INTRUS"); // eroare de compilare: SafeStack nu are add
        // safe.get(0);           // eroare de compilare

        // Întrebare pentru sală: Stack ESTE un Vector? Sau Stack ARE o listă înăuntru?
    }
}
