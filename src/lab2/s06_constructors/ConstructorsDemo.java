package lab2.s06_constructors;

/**
 * Slide 6: constructor implicit, supraîncărcare, this(...), copy constructor.
 */
class Date {
    int day = 1;
    // Fără constructor scris → compilatorul adaugă Date() {}
}

class SpecialDate {
    int day = 1;

    SpecialDate(int day) {
        this.day = day;
    }

    // Am scris un constructor → cel implicit NU mai este generat
}

class Car {
    String model;
    int doors;

    Car(String model, int doors) {
        System.out.println("  Car(String, int)");
        this.model = model;
        this.doors = doors;
    }

    Car(String model) {
        this(model, 4);                       // refolosim, nu duplicăm
        System.out.println("  Car(String)");
    }

    Car() {
        this("Dacia");                        // lanț: () → (String) → (String, int)
        System.out.println("  Car()");
    }

    // copy constructor
    Car(Car other) {
        this(other.model, other.doors);
        System.out.println("  Car(Car)");
    }

    @Override
    public String toString() {
        return model + " cu " + doors + " uși";
    }
}

public class ConstructorsDemo {
    public static void main(String[] args) {
        Date d = new Date();                  // merge — constructor implicit
        System.out.println("Date.day = " + d.day);

        // SpecialDate bad = new SpecialDate();   // decomentați → eroare de compilare
        SpecialDate sd = new SpecialDate(30);
        System.out.println("SpecialDate.day = " + sd.day);

        System.out.println("\nnew Car():");
        Car c1 = new Car();
        System.out.println("→ " + c1);

        System.out.println("\nnew Car(\"Tesla\", 2):");
        Car c2 = new Car("Tesla", 2);
        System.out.println("→ " + c2);

        System.out.println("\nnew Car(c2) — copy:");
        Car c3 = new Car(c2);
        c3.doors = 5;
        System.out.println("→ copia:    " + c3);
        System.out.println("→ originalul: " + c2 + "  (neschimbat)");

        // Atribuire ≠ copiere
        Car alias = c2;
        alias.doors = 99;
        System.out.println("\nalias.doors = 99 → c2: " + c2 + "  (aceeași instanță!)");
    }
}
