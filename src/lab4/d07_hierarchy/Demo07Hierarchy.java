package lab4.d07_hierarchy;

import java.util.List;

/**
 * Slide 11: ierarhie care combina clase abstracte si interfete (exemplul de pe pagina).
 *
 *   Living (abstract: eat(); concret: grow())
 *     └─ Animal (abstract: makeSound())
 *          ├─ Dog  implements Domesticable, Guardable
 *          ├─ Cat  implements Domesticable
 *          └─ Bird implements Flyable
 *
 * Clasele abstracte spun CE ESTE obiectul; interfetele spun CE STIE SA FACA.
 */
abstract class Living {
    private int age;

    public void grow() {
        age++;
    }

    public int getAge() {
        return age;
    }

    public abstract void eat();
}

abstract class Animal extends Living {
    public abstract String makeSound();
}

interface Domesticable {
    void obeyOwner();
}

interface Guardable {
    void guard();
}

interface Flyable {
    void fly();
}

class Dog extends Animal implements Domesticable, Guardable {
    @Override public void eat() { System.out.println("  Dog mananca boabe"); }
    @Override public String makeSound() { return "Ham!"; }
    @Override public void obeyOwner() { System.out.println("  Dog: sezi -> sta"); }
    @Override public void guard() { System.out.println("  Dog: pazeste curtea"); }
}

class Cat extends Animal implements Domesticable {
    @Override public void eat() { System.out.println("  Cat mananca peste"); }
    @Override public String makeSound() { return "Miau!"; }
    @Override public void obeyOwner() { System.out.println("  Cat: ...te ignora (dar tehnic implementeaza)"); }
}

class Bird extends Animal implements Flyable {
    @Override public void eat() { System.out.println("  Bird ciuguleste seminte"); }
    @Override public String makeSound() { return "Cip-cirip!"; }
    @Override public void fly() { System.out.println("  Bird: zboara"); }
}

public class Demo07Hierarchy {
    public static void main(String[] args) {
        List<Animal> zoo = List.of(new Dog(), new Cat(), new Bird());

        System.out.println("== Prin tipul abstract Animal ==");
        for (Animal a : zoo) {
            a.grow();
            a.eat();
            System.out.println("  " + a.getClass().getSimpleName() + " spune " + a.makeSound()
                    + " (varsta " + a.getAge() + ")");
        }

        System.out.println("\n== Prin capabilitati (interfete) ==");
        for (Animal a : zoo) {
            if (a instanceof Guardable g) {
                g.guard();
            }
            if (a instanceof Flyable f) {
                f.fly();
            }
        }
        // Intrebare pentru sala: de ce aici instanceof e acceptabil, dar la Lab 3 era "code smell"?

        // DEMO EROARE: Living e abstracta
        // Living l = new Living();
    }
}
