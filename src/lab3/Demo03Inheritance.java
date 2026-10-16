package lab3;

/**
 * Slide 5 — moștenire: Object → Animal → Mammal → Cat.
 *
 * Arată ce membri vede fiecare clasă și ordinea constructorilor.
 */
public class Demo03Inheritance {

    static class Animal {
        float weight = 10.2f;

        Animal() { System.out.println("  constructor Animal"); }

        void eat()   { System.out.println("  The animal is eating"); }
        void sleep() { System.out.println("  The animal is sleeping"); }
    }

    static class Mammal extends Animal {
        int heartRate = 90;

        Mammal() {
            // Animal e deja construit, deci weight are valoare
            System.out.println("  constructor Mammal (weight moștenit = " + weight + ")");
        }

        void breathe() { System.out.println("  The mammal is breathing"); }
    }

    static class Cat extends Mammal {
        boolean longHair = true;

        Cat() { System.out.println("  constructor Cat"); }

        void purr() { System.out.println("  The cat is purring"); }
    }

    public static void main(String[] args) {
        System.out.println("== new Cat() — ordinea constructorilor ==");
        Cat cat = new Cat();

        System.out.println();
        System.out.println("== Cat vede tot lanțul ==");
        cat.purr();
        cat.breathe();
        cat.eat();
        cat.sleep();
        System.out.println("  weight = " + cat.weight + ", heartRate = " + cat.heartRate);

        System.out.println();
        System.out.println("== Animal vede doar ce e al lui ==");
        Animal animal = new Animal();
        animal.eat();
        // animal.purr();     // eroare de compilare: Animal nu are purr()
        // animal.breathe();  // eroare de compilare

        System.out.println();
        System.out.println("== Și Object e în lanț ==");
        System.out.println("  superclasa lui Cat:    " + Cat.class.getSuperclass().getSimpleName());
        System.out.println("  superclasa lui Mammal: " + Mammal.class.getSuperclass().getSimpleName());
        System.out.println("  superclasa lui Animal: " + Animal.class.getSuperclass().getSimpleName());

        // Notă: pe pagina laboratorului metoda e scrisă „public static void Main(...)”.
        // Cu M mare nu e punct de intrare — IntelliJ nici nu afișează săgeata verde.
    }
}
