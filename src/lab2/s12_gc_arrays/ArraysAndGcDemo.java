package lab2.s12_gc_arrays;

/**
 * Slide 12: array-uri de obiecte și eligibilitatea pentru Garbage Collection.
 */
class Student {
    String name;

    Student() {
        this.name = "fără nume";
    }

    Student(String name) {
        this.name = name;
    }
}

public class ArraysAndGcDemo {
    public static void main(String[] args) {
        Student[] arr = new Student[3];
        System.out.println("arr[0] după new Student[3]: " + arr[0]);   // null

        try {
            arr[0].name = "Mihai";             // NPE — nu există obiect
        } catch (NullPointerException e) {
            System.out.println("NPE: array-ul ține referințe, nu obiecte");
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Student("Student " + i);
        }
        arr[0].name = "Mihai";
        for (Student s : arr) {
            System.out.println("  " + s.name);
        }

        // --- Eligibilitate GC ---
        Student a = new Student("A");
        a = null;                              // obiectul "A" nu mai are referințe → eligibil

        // Referințe circulare: tot eligibile, GC lucrează pe accesibilitate
        Node n1 = new Node();
        Node n2 = new Node();
        n1.next = n2;
        n2.next = n1;
        n1 = null;
        n2 = null;                             // ambele inaccesibile din program → eligibile

        Runtime rt = Runtime.getRuntime();
        long before = rt.totalMemory() - rt.freeMemory();
        for (int i = 0; i < 200_000; i++) {
            new Student("temp" + i);           // gunoi imediat
        }
        long after = rt.totalMemory() - rt.freeMemory();
        System.gc();                           // doar o sugestie
        long afterGc = rt.totalMemory() - rt.freeMemory();
        System.out.printf("%nMemorie folosită: înainte %d KB, după alocări %d KB, după gc() %d KB%n",
                before / 1024, after / 1024, afterGc / 1024);
        System.out.println("(valorile variază de la rulare la rulare — JVM decide)");
    }
}

class Node {
    Node next;
}
