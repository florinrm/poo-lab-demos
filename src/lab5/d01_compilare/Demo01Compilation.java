package lab5.d01_compilare;

// Slide 3 — fiecare clasă internă devine un .class separat, cu $ în nume.
// Rulează, apoi uită-te în target/classes/org/poo/lab5/d01_compilare/.

interface Engine {
    int getFuelCapacity();
}

class Car {
    class OttoEngine implements Engine {          // inner (membru)
        public int getFuelCapacity() { return 11; }
    }

    static class Helper {                         // static nested — are main, se poate rula direct
        public static void main(String[] args) {
            System.out.println("Car$Helper rulează singur: " + Helper.class.getName());
        }
    }

    Engine anonymous() {
        return new Engine() {                     // anonimă → Car$1
            public int getFuelCapacity() { return 20; }
        };
    }

    Engine local() {
        class DieselEngine implements Engine {    // locală → Car$1DieselEngine
            public int getFuelCapacity() { return 30; }
        }
        return new DieselEngine();
    }

    Engine lambda() {
        return () -> 40;                          // lambda → NU generează Car$...class
    }
}

public class Demo01Compilation {
    public static void main(String[] args) {
        Car car = new Car();
        System.out.println("inner:     " + car.new OttoEngine().getClass().getName());
        System.out.println("static:    " + Car.Helper.class.getName());
        System.out.println("anonimă:   " + car.anonymous().getClass().getName());
        System.out.println("locală:    " + car.local().getClass().getName());
        System.out.println("lambda:    " + car.lambda().getClass().getName() + "  (generată la rulare)");
    }
}
