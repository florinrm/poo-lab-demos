package lab3;

/**
 * Slide 12 — „Ce se afișează?” + super.metoda() în metoda suprascrisă.
 *
 * Proiectează codul, lasă sala să ghicească fiecare linie, apoi rulează.
 */
public class Demo10WhatPrints {

    static class Animal {
        public void makeSound() { System.out.println("Animal makes sound"); }
    }

    static class Dog extends Animal {
        @Override
        public void makeSound() { System.out.println("Bark!"); }
    }

    static class Parent {
        void sayHello() { System.out.println("Hello from Parent"); }
    }

    static class Child extends Parent {
        @Override
        void sayHello() {
            super.sayHello(); // extindem, nu rescriem complet
            System.out.println("Hello from Child");
        }
    }

    public static void main(String[] args) {
        Animal a = new Animal();
        a.makeSound();         // ?

        Animal b = new Dog();
        b.makeSound();         // ?

        a = new Dog();
        a.makeSound();         // ?

        System.out.println("----");
        new Child().sayHello(); // ?
    }
}
