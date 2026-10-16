package lab5.d03_ascundere;

// Slide 5 — clasă internă private: exteriorul vede doar interfața.

interface Engine {
    int getFuelCapacity();
}

class Car {
    private class OttoEngine implements Engine {
        public int getFuelCapacity() { return 11; }
        public void secretTuning() { System.out.println("tuning"); }
    }

    public Engine getEngine() {
        return new OttoEngine();                  // upcast la Engine
    }
}

public class Demo03HidingImpl {
    public static void main(String[] args) {
        Engine e = new Car().getEngine();
        System.out.println("capacitate: " + e.getFuelCapacity());
        System.out.println("tipul real: " + e.getClass().getSimpleName() + " — dar nu-l pot numi în cod");

        // DEMO EROARE: tipul e private în Car
        // Car.OttoEngine o = null;

        // DEMO EROARE: referința e Engine, metoda nu există pe interfață
        // e.secretTuning();
    }
}
