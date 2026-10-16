package lab2.s13_static;

/**
 * Slide 13: membri statici, constante, contor de instanțe, ordinea blocurilor statice.
 */
class Apple {
    static final float EARTH_ACC = 9.8f;       // constantă
    static float gravAcc = EARTH_ACC;          // comună tuturor merelor
    private static int instanceCount;

    float mass;

    static {
        System.out.println("  [static block] clasa Apple a fost încărcată");
        instanceCount = 0;
    }

    Apple(float mass) {
        this.mass = mass;
        instanceCount++;
        System.out.println("  [constructor] măr nou, total = " + instanceCount);
    }

    float getWeight() {
        return mass * gravAcc;                  // static accesat direct din clasă
    }

    static int getInstanceCount() {
        // return (int) mass;                   // decomentați → eroare: mass nu e static
        // return this.instanceCount;           // decomentați → eroare: nu există this
        return instanceCount;
    }
}

public class StaticDemo {
    public static void main(String[] args) {
        System.out.println("main a pornit — Apple încă nu e încărcată");

        System.out.println("\nPrima referire la Apple:");
        System.out.println("instanceCount = " + Apple.getInstanceCount());

        System.out.println("\nCreăm două mere:");
        Apple a1 = new Apple(0.2f);
        Apple a2 = new Apple(0.3f);
        System.out.println("Greutate a1 pe Pământ: " + a1.getWeight());

        Apple.gravAcc = 3.7f;                   // toate merele cad acum pe Marte
        System.out.println("\nDupă Apple.gravAcc = 3.7f:");
        System.out.println("Greutate a1: " + a1.getWeight());
        System.out.println("Greutate a2: " + a2.getWeight());
        System.out.println("a1.gravAcc (merge, dar nerecomandat): " + a1.gravAcc);

        System.out.println("\nUtilitar static din JDK: Math.sqrt(2) = " + Math.sqrt(2));
    }
}
