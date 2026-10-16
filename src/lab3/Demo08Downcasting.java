package lab3;

/**
 * Slide 10 — downcasting, instanceof și de ce îl evităm.
 */
public class Demo08Downcasting {

    static class Animal {
        void play() { System.out.println("  Animal plays"); }
    }

    static class Dog extends Animal {
        void fetch() { System.out.println("  Dog fetches the ball"); }
        @Override void play() { fetch(); }
    }

    static class Cat extends Animal {
        void chaseLaser() { System.out.println("  Cat chases the laser"); }
        @Override void play() { chaseLaser(); }
    }

    static class Parrot extends Animal {
        void talk() { System.out.println("  Parrot says: Hello!"); }
        @Override void play() { talk(); }
    }

    /** Varianta anti-OOP: un if pentru fiecare tip. */
    static void playWithInstanceof(Animal a) {
        if (a instanceof Dog d) {          // pattern matching (Java 16+)
            d.fetch();
        } else if (a instanceof Cat) {
            ((Cat) a).chaseLaser();        // forma clasică, ca pe pagină
        } else {
            System.out.println("  ??? tip necunoscut: " + a.getClass().getSimpleName());
        }
        // Am adăugat Parrot și am uitat de funcția asta → bug.
    }

    public static void main(String[] args) {
        System.out.println("== Downcast reușit ==");
        Animal a = new Dog();
        ((Dog) a).fetch();

        System.out.println();
        System.out.println("== Downcast greșit: compilează, dar crapă la rulare ==");
        try {
            Cat c = (Cat) a;
            c.chaseLaser();
        } catch (ClassCastException e) {
            System.out.println("  ClassCastException: " + e.getMessage());
        }

        Animal[] animals = { new Dog(), new Cat(), new Parrot() };

        System.out.println();
        System.out.println("== Cu instanceof (lanț de if-uri) ==");
        for (Animal animal : animals) {
            playWithInstanceof(animal);
        }

        System.out.println();
        System.out.println("== Cu polimorfism: fiecare animal știe singur ==");
        for (Animal animal : animals) {
            animal.play();
        }
        // Un animal nou = o clasă nouă cu play() suprascris. Codul de mai sus nu se schimbă
        // (Open-Closed Principle).
    }
}
