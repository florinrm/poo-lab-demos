package lab3;

/**
 * Slide 9 — upcasting: referința devine mai generală, instanța rămâne aceeași.
 */
public class Demo07Upcasting {

    static class Animal {
        void eat() { System.out.println("  Animal eats"); }
    }

    static class Dog extends Animal {
        @Override void eat() { System.out.println("  Dog eats a bone"); }
        void wagTail()       { System.out.println("  Dog wags tail"); }
    }

    static class Cat extends Animal {
        @Override void eat() { System.out.println("  Cat eats fish"); }
    }

    static class Rabbit extends Animal {
        @Override void eat() { System.out.println("  Rabbit eats a carrot"); }
    }

    /** Varianta neextensibilă: câte o metodă pentru fiecare tip. */
    static class OverloadedShelter {
        void feedAnimal(Dog d)    { d.eat(); }
        void feedAnimal(Cat c)    { c.eat(); }
        void feedAnimal(Rabbit r) { r.eat(); }
        // ... și încă una pentru fiecare animal nou
    }

    /** Varianta cu upcasting: o singură metodă. */
    static class Shelter {
        void feedAnimal(Animal animal) { animal.eat(); }
    }

    public static void main(String[] args) {
        Animal a = new Dog(); // upcasting automat
        a.eat();
        // a.wagTail(); // DEMO EROARE: cannot find symbol — compilatorul vede doar Animal

        System.out.println("  clasa reală a lui a: " + a.getClass().getSimpleName());

        System.out.println();
        System.out.println("== Shelter cu o singură metodă ==");
        Shelter shelter = new Shelter();
        shelter.feedAnimal(new Dog());
        shelter.feedAnimal(new Cat());
        shelter.feedAnimal(new Rabbit());

        System.out.println();
        System.out.println("== Un vector de Animal cu tipuri diferite ==");
        Animal[] animals = { new Dog(), new Cat(), new Rabbit(), new Animal() };
        for (Animal animal : animals) {
            animal.eat(); // ce versiune se apelează? cea a instanței
        }
    }
}
