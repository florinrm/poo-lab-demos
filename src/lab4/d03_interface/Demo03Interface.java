package lab4.d03_interface;

/**
 * Slide 6: interfata = contract; interfata ca TIP.
 *
 * De aratat:
 *  - Automobile si Lawnmower NU au parinte comun (in afara de Object), dar ambele sunt Driveable;
 *  - campurile din interfata sunt public static final (constante);
 *  - metoda implementata trebuie sa fie public.
 */
interface Driveable {
    int MAX_SPEED = 200; // implicit public static final

    boolean startEngine();

    void stopEngine();

    float accelerate(float acc);
}

class Automobile implements Driveable {
    private boolean engineRunning;
    private float speed;

    @Override
    public boolean startEngine() {
        engineRunning = true;
        System.out.println("  Automobile: vroom");
        return true;
    }

    @Override
    public void stopEngine() {
        engineRunning = false;
        System.out.println("  Automobile: oprit");
    }

    @Override
    public float accelerate(float acc) {
        speed = Math.min(speed + acc, MAX_SPEED);
        return speed;
    }
}

class Lawnmower implements Driveable {
    @Override
    public boolean startEngine() {
        System.out.println("  Lawnmower: trage de sfoara... brrrr");
        return true;
    }

    @Override
    public void stopEngine() {
        System.out.println("  Lawnmower: liniste");
    }

    @Override
    public float accelerate(float acc) {
        return Math.min(acc, 8); // masina de tuns iarba nu prea accelereaza
    }

    // DEMO EROARE: stergeti "public" din stopEngine() de mai sus
    // -> "attempting to assign weaker access privileges; was public"
}

public class Demo03Interface {

    // interfata ca tip de parametru: metoda nu stie si nu-i pasa ce clasa primeste
    static void testDrive(Driveable d) {
        d.startEngine();
        System.out.println("  viteza dupa accelerare: " + d.accelerate(250));
        d.stopEngine();
    }

    public static void main(String[] args) {
        Driveable vehicle;

        vehicle = new Automobile();
        testDrive(vehicle);

        vehicle = new Lawnmower();
        testDrive(vehicle);

        System.out.println("\nConstanta din interfata: Driveable.MAX_SPEED = " + Driveable.MAX_SPEED);

        // DEMO EROARE 1: campul e final
        // Driveable.MAX_SPEED = 300;

        // DEMO EROARE 2: interfata nu se instantiaza
        // Driveable d = new Driveable();
    }
}
