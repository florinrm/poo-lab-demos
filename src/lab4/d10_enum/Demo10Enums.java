package lab4.d10_enum;

/**
 * Slide 16 [Nice to know]: enum.
 *
 *  - fiecare constanta ESTE un obiect de tipul enum-ului (Direction.NORTH e de tip Direction);
 *  - values(), name(), ordinal(), valueOf();
 *  - switch pe enum (forma clasica si forma cu sageata, Java 14+);
 *  - enum cu campuri, constructor si metode (Planet - valorile de pe pagina);
 *  - comportament diferit pe constanta (metoda abstracta in enum).
 */
enum Direction {
    NORTH, SOUTH, EAST, WEST
}

enum Planet {
    MERCURY(3.3e+23, 2.4e6),
    VENUS(4.87e+24, 6.1e6),
    EARTH(5.97e+24, 6.4e6);

    private static final double G = 6.67300E-11;
    private final double mass;   // kg
    private final double radius; // m

    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    public double surfaceGravity() {
        return G * mass / (radius * radius);
    }
}

enum TrafficLight {
    RED {
        @Override
        TrafficLight next() { return GREEN; }
    },
    GREEN {
        @Override
        TrafficLight next() { return YELLOW; }
    },
    YELLOW {
        @Override
        TrafficLight next() { return RED; }
    };

    abstract TrafficLight next(); // metoda abstracta intr-un enum: fiecare constanta o implementeaza
}

public class Demo10Enums {
    public static void main(String[] args) {
        Direction dir = Direction.NORTH;
        System.out.println("Tipul lui NORTH: " + dir.getClass().getSimpleName());
        System.out.println("NORTH instanceof Direction: " + (dir instanceof Direction));

        System.out.println("\n== values / name / ordinal ==");
        for (Direction d : Direction.values()) {
            System.out.println("  " + d.name() + " -> ordinal " + d.ordinal());
        }
        System.out.println("valueOf(\"EAST\") = " + Direction.valueOf("EAST"));
        // DEMO EROARE (la rulare): Direction.valueOf("east") -> IllegalArgumentException
        // DEMO EROARE (la compilare): Direction d = 2;   sau   Direction d = "NORTH";

        System.out.println("\n== switch cu sageata (fara break, fara fall-through) ==");
        String msg = switch (dir) {
            case NORTH -> "Mergeti spre nord";
            case SOUTH -> "Mergeti spre sud";
            case EAST, WEST -> "Mergeti lateral";
        }; // fara default: compilatorul stie ca am acoperit toate constantele
        System.out.println(msg);

        System.out.println("\n== Planet (valorile de pe pagina) ==");
        double massOnEarth = 70;
        for (Planet p : Planet.values()) {
            System.out.printf("  Greutatea ta pe %s este %.2f N (g = %.2f m/s^2)%n",
                    p.name(), p.surfaceGravity() * massOnEarth, p.surfaceGravity());
        }

        System.out.println("\n== Comportament pe constanta ==");
        TrafficLight t = TrafficLight.RED;
        for (int i = 0; i < 4; i++) {
            System.out.print(t + " -> ");
            t = t.next();
        }
        System.out.println(t);

        System.out.println("\nComparatie cu == e sigura la enum: " + (Direction.valueOf("NORTH") == Direction.NORTH));
    }
}
