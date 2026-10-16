package lab4.d01_abstract;

/**
 * Slide 3-4: clasa abstracta, metoda abstracta, lant de clase abstracte.
 *
 * De aratat:
 *  - constructorul clasei abstracte RULEAZA (prin super(name)), desi clasa nu poate fi instantiata;
 *  - Car e tot abstracta: implementeaza startEngine(), dar adauga openTrunk();
 *  - abia Sedan e concreta -> abia de ea putem face obiecte;
 *  - prin referinta Vehicle apelam startEngine() fara sa stim tipul concret.
 */
abstract class Vehicle {
    private String name;

    Vehicle(String name) {
        this.name = name;
        System.out.println("  [Vehicle()] constructor rulat pentru " + name);
    }

    public abstract void startEngine();

    public String getName() {
        return name;
    }
}

abstract class Car extends Vehicle {
    Car(String name) {
        super(name);
    }

    public abstract void openTrunk();

    @Override
    public void startEngine() {
        System.out.println("  Car engine started!");
    }
}

class Sedan extends Car {
    Sedan(String name) {
        super(name);
    }

    @Override
    public void openTrunk() {
        System.out.println("  Trunk opened in the back of the car!");
    }
}

class ElectricScooter extends Vehicle {
    ElectricScooter(String name) {
        super(name);
    }

    @Override
    public void startEngine() {
        System.out.println("  *bip* scooter pornit, fara zgomot");
    }
}

public class Demo01AbstractClass {
    public static void main(String[] args) {
        System.out.println("== 1. Cream un Sedan (constructorii abstracti ruleaza in lant) ==");
        Sedan sedan = new Sedan("Logan");
        sedan.startEngine();
        sedan.openTrunk();

        System.out.println("\n== 2. Referinta de tip abstract, obiect concret ==");
        Vehicle[] garage = { new Sedan("Octavia"), new ElectricScooter("Xiaomi") };
        for (Vehicle v : garage) {
            System.out.print(v.getName() + ": ");
            v.startEngine(); // fiecare isi stie implementarea
        }

        // DEMO EROARE 1: clasa abstracta nu se poate instantia
        // Vehicle v = new Vehicle("generic");   // Vehicle is abstract; cannot be instantiated

        // DEMO EROARE 2: nici Car (inca are openTrunk() abstracta)
        // Car c = new Car("x");

        // DEMO EROARE 3: exemplul de pe pagina -> Sedan nu are constructor fara parametri
        // Sedan s = new Sedan();               // constructor Sedan in class Sedan cannot be applied

        // DEMO EROARE 4: stergeti "abstract" din fata lui Car -> compilatorul cere openTrunk()
    }
}
