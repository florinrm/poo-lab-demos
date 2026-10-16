package lab2.demo.s10_memory;

/**
 * Slide 10: Stack vs. Heap.
 * Puneți un breakpoint pe linia marcată și rulați în Debug:
 * x apare cu valoare, s apare ca Student@ID (referință către heap).
 */
class Student {
    private final String name;
    private final int grade;

    Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    String getName() {
        return name;
    }
}

public class StackHeapDemo {

    static void test() {
        int x = 10;                              // pe stack
        Student none = null;                     // referință fără obiect
        Student s = new Student("Mihai", 8);     // referința pe stack, obiectul în heap
        Student same = s;                        // a doua referință, ACELAȘI obiect

        System.out.println("x = " + x);          // <-- breakpoint aici
        System.out.println("none = " + none);
        System.out.println("s    = " + s);       // fără toString(): NumeClasa@hash
        System.out.println("same == s ? " + (same == s));
        System.out.println("identityHashCode s:    " + System.identityHashCode(s));
        System.out.println("identityHashCode same: " + System.identityHashCode(same));
    }   // frame-ul dispare aici; obiectul rămâne în heap până îl colectează GC

    public static void main(String[] args) {
        test();
    }
}
