package lab5.d02_inner;

// Slide 4 — clasa internă normală: are nevoie de un obiect Car.

class Car {
    private String model;
    private int speed = 100;

    Car(String model) { this.model = model; }

    class OttoEngine {
        private int speed = 7000;                 // același nume ca în Car → umbrește

        void print() {
            System.out.println("model (private din Car): " + model);
            System.out.println("speed       = " + speed + "   (al motorului)");
            System.out.println("this.speed  = " + this.speed);
            System.out.println("Car.this.speed = " + Car.this.speed + "   (al mașinii)");
        }
    }

    void peekInside(OttoEngine e) {
        // și invers: clasa externă vede private-ul clasei interne
        System.out.println("Car citește OttoEngine.speed (private): " + e.speed);
    }
}

public class Demo02InnerClass {
    public static void main(String[] args) {
        Car tesla = new Car("Tesla");
        Car dacia = new Car("Dacia");

        Car.OttoEngine e1 = tesla.new OttoEngine();
        Car.OttoEngine e2 = dacia.new OttoEngine();
        e1.print();
        System.out.println("---");
        e2.print();                               // fiecare motor știe de mașina lui
        System.out.println("---");
        tesla.peekInside(e1);

        // DEMO EROARE: fără instanță Car nu există OttoEngine
        // Car.OttoEngine e3 = new Car.OttoEngine();
    }
}
