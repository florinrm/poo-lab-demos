package lab5.d07_locale;

// Slide 10 — clase locale (în metodă / bloc) și moștenirea unei clase interne.

interface Engine {
    int getFuelCapacity();
}

class Car {
    Engine fromMethod() {
        class OttoEngine implements Engine {
            public int getFuelCapacity() { return 11; }
        }
        return new OttoEngine();
    }

    Engine fromBlock(boolean condition) {
        if (condition) {
            class BlockEngine implements Engine {
                public int getFuelCapacity() { return 22; }
            }
            return new BlockEngine();
        }
        // DEMO EROARE: BlockEngine e vizibilă doar în blocul if
        // return new BlockEngine();
        return null;
    }

    class Motor {                                 // inner, va fi extinsă din afară
        void describe() { System.out.println("motor generic al mașinii " + Car.this.hashCode()); }
    }
}

class TurboMotor extends Car.Motor {
    TurboMotor(Car car) {
        car.super();                              // leagă motorul de un Car anume
    }

    // DEMO EROARE: nu există un Car implicit de care să se lege
    // TurboMotor() { }

    @Override
    void describe() {
        System.out.print("turbo -> ");
        super.describe();
    }
}

public class Demo07LocalAndInheritance {
    public static void main(String[] args) throws Exception {
        Car car = new Car();
        System.out.println(car.fromMethod().getClass().getName());
        System.out.println("fromBlock(false) = " + car.fromBlock(false));

        // clasa din if există chiar dacă if-ul n-a fost niciodată adevărat
        Class<?> c = Class.forName("org.poo.lab5.d07_locale.Car$1BlockEngine");
        System.out.println("există la rulare: " + c.getName());

        new TurboMotor(car).describe();
    }
}
