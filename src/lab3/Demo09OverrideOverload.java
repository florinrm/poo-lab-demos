package lab3;

/**
 * Slide 11 — override (decis la rulare) vs. overload (decis la compilare).
 */
public class Demo09OverrideOverload {

    static class Animal {
        public void makeSound() { System.out.println("  Animal makes sound"); }

        static void info() { System.out.println("  Animal.info() (static)"); }

        Animal baby() { return new Animal(); }
    }

    static class Dog extends Animal {
        // Typo: „makesound” cu s mic. Fără @Override compilează, dar NU suprascrie nimic.
        public void makesound() { System.out.println("  Bark!"); }

        // DEMO EROARE: decomentează și primești „method does not override or implement
        // a method from a supertype”. De asta punem mereu @Override.
        // @Override
        // public void makesound() { System.out.println("  Bark!"); }

        // Metodă static cu același nume: ASCUNDE (method hiding), nu suprascrie.
        static void info() { System.out.println("  Dog.info() (static)"); }

        // Return covariant: Dog în loc de Animal — e tot override.
        @Override Dog baby() { return new Dog(); }

        // DEMO EROARE: acces mai restrictiv decât în părinte (public → private)
        // @Override private void makeSound() { }
    }

    static class Vet {
        void treat(Animal a) { System.out.println("  treat(Animal)"); }
        void treat(Dog d)    { System.out.println("  treat(Dog)"); }
    }

    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        System.out.println("== Override ratat din cauza unui typo ==");
        Animal a = new Dog();
        a.makeSound();   // Animal makes sound — nu Bark!

        System.out.println();
        System.out.println("== Static: se alege după referință, nu după instanță ==");
        a.info();        // Animal.info() — deși obiectul e Dog
        Dog.info();

        System.out.println();
        System.out.println("== Overload: se alege la compilare, după tipul referinței ==");
        Vet vet = new Vet();
        Dog d = new Dog();
        vet.treat(d);    // treat(Dog)
        vet.treat(a);    // treat(Animal)! obiectul e Dog, dar referința e Animal

        System.out.println();
        System.out.println("== Return covariant ==");
        Dog puppy = d.baby(); // nu e nevoie de cast
        System.out.println("  d.baby() întoarce " + puppy.getClass().getSimpleName());
    }
}
