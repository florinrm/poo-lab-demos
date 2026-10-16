package lab3;

/**
 * Slide 15 — [Optional] ce face JVM-ul în spate.
 *
 * Rulează clasa, apoi uită-te în bytecode (pașii sunt în README):
 *   javap -c -p target/classes/org/poo/lab3/Demo13Dispatch.class
 * Caută invokevirtual (metode de instanță) și invokestatic (metode statice) în main.
 */
public class Demo13Dispatch {

    static class Animal {
        static int created = 0; // în HotSpot stă în heap, în obiectul Class, nu în Metaspace

        Animal() { created++; }

        void makeSound()   { System.out.println("  generic"); }
        static void info() { System.out.println("  Animal class"); }
    }

    static class Dog extends Animal {
        @Override void makeSound() { System.out.println("  woof"); }
        static void info()         { System.out.println("  Dog class"); }
    }

    public static void main(String[] args) {
        Animal a = new Dog();

        System.out.println("a.makeSound()  -> invokevirtual, caută în vtable-ul clasei reale:");
        a.makeSound();

        System.out.println("Animal.info()  -> invokestatic, fără vtable:");
        Animal.info();

        System.out.println("Dog.info()     -> invokestatic:");
        Dog.info();

        System.out.println();
        System.out.println("Animal.created = " + Animal.created + " (variabilă static, una per clasă)");
        new Animal();
        new Dog();
        System.out.println("Animal.created = " + Animal.created);
    }
}
