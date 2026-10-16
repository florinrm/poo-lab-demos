package lab4.d05_collisions;

import java.util.List;

/**
 * Slide 8: coliziuni de nume la combinarea interfetelor.
 *
 *  - aceeasi semnatura + acelasi tip de return -> o singura implementare, totul OK;
 *  - tipuri de return INCOMPATIBILE (int vs String) -> eroare de compilare;
 *  - tipuri de return COMPATIBILE (Object vs String, Iterable vs List) -> merge!
 *    (return covariant: alegi tipul cel mai specific). Pagina sugereaza ca orice
 *    diferenta de tip de return e conflict - nu e chiar asa.
 */
interface A {
    int run();
}

interface B {
    int run();
}

class ABRunner implements A, B {
    @Override
    public int run() {
        return 42; // o singura implementare satisface ambele contracte
    }
}

interface C {
    String run();
}

// DEMO EROARE: decomentati clasa -> "ACFail is not abstract and does not override abstract method run() in C"
// (nicio metoda run() nu poate intoarce simultan int si String; incercati si cu String run())
// class ACFail implements A, C {
//     public int run() { return 0; }
// }

interface Source {
    Object get();
}

interface TextSource {
    String get();
}

class Hello implements Source, TextSource {
    @Override
    public String get() { // String e si Object -> respecta ambele contracte
        return "hello";
    }
}

interface Shelf {
    Iterable<String> items();
}

interface Catalog {
    List<String> items();
}

class Library implements Shelf, Catalog {
    @Override
    public List<String> items() { // List extinde Iterable
        return List.of("Ion", "Enigma Otiliei");
    }
}

public class Demo05NameCollisions {
    public static void main(String[] args) {
        ABRunner r = new ABRunner();
        A a = r;
        B b = r;
        System.out.println("A.run() = " + a.run() + ", B.run() = " + b.run());

        Hello h = new Hello();
        Source src = h;
        TextSource txt = h;
        Object o = src.get();
        String s = txt.get();
        System.out.println("Source.get() = " + o + ", TextSource.get().length() = " + s.length());

        System.out.println("Library.items() = " + new Library().items());
    }
}
