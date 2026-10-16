package lab3;

/**
 * Slide 3 — cele patru moduri de a inițializa un obiect conținut
 * și ordinea în care se execută ele de fapt.
 *
 * Întrebare pentru sală, înainte de rulare: în ce ordine apar mesajele 1, 2, 3?
 */
public class Demo01Initialization {

    static class Engine {
        Engine(String how) {
            System.out.println("   -> Engine creat: " + how);
        }
    }

    static class Vehicle {
        Vehicle() {
            System.out.println("1. constructorul Vehicle (părintele)");
        }
    }

    static class Car extends Vehicle {
        // (1) eager — la declarare
        private Engine eager = new Engine("eager, la declarare");

        // (2) bloc de inițializare
        private Engine fromBlock;
        {
            System.out.println("2. blocul de inițializare din Car");
            fromBlock = new Engine("din blocul de inițializare");
        }

        // (3) în constructor — primit din exterior
        private Engine fromConstructor;

        // (4) lazy — creat doar la nevoie
        private Engine lazy;

        Car(Engine engine) {
            super(); // pus explicit doar ca să se vadă unde e
            System.out.println("3. corpul constructorului Car");
            this.fromConstructor = engine;
        }

        Engine getLazyEngine() {
            if (lazy == null) {
                lazy = new Engine("lazy, la primul apel");
            }
            return lazy;
        }
    }

    public static void main(String[] args) {
        System.out.println("== new Car(new Engine(...)) ==");
        Car car = new Car(new Engine("primit din exterior"));

        System.out.println();
        System.out.println("== getLazyEngine() de două ori ==");
        car.getLazyEngine();
        car.getLazyEngine(); // nu mai creează nimic
        System.out.println("(motorul lazy a fost creat o singură dată)");

        // Observație: blocul de inițializare NU rulează „înaintea constructorului”,
        // ci după super(...) și înaintea restului corpului constructorului.
        // Inițializările la declarare și blocurile rulează în ordinea în care apar în cod.
    }
}
