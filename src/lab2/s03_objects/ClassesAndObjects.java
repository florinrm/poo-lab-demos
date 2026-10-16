package lab2.s03_objects;

/**
 * Slide 3-4: Clasă vs. obiect, instanțiere, valori implicite, NullPointerException.
 */
class Apple {
    // câmpuri — primesc valori implicite dacă nu le inițializăm
    float mass;
    float diameter = 1.0f;
    int x, y;
    boolean ripe;
    String variety;      // obiect → null implicit

    // metodă
    boolean isFalling() {
        return false;
    }
}

public class ClassesAndObjects {
    public static void main(String[] args) {
        Apple a1;               // doar referința
        a1 = new Apple();       // abia acum avem obiect în heap
        Apple a2 = new Apple(); // declarare + instanțiere

        System.out.println("Apple a1:");
        System.out.println("  mass:     " + a1.mass);
        System.out.println("  diameter: " + a1.diameter);
        System.out.println("  position: (" + a1.x + ", " + a1.y + ")");
        System.out.println("  ripe:     " + a1.ripe);
        System.out.println("  variety:  " + a1.variety);
        System.out.println("  falling:  " + a1.isFalling());

        // Două obiecte = două zone de memorie independente
        a1.mass = 12;
        a2.mass = 15;
        System.out.println("\na1.mass = " + a1.mass + ", a2.mass = " + a2.mass);

        // Variabilele locale NU primesc valori implicite:
//         int local;
//         System.out.println(local);   // decomentați → eroare de compilare

        // String — caz special, fără new
        String s1 = "Ion";
        String s2 = "Popescu";
        System.out.println("\nString: " + s1 + " " + s2);

        // NullPointerException: membru accesat pe o referință null
        Apple missing = null;
        try {
            System.out.println(missing.mass);
        } catch (NullPointerException e) {
            System.out.println("\nNPE: " + e.getMessage());
        }
    }
}
