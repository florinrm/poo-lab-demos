package lab7.s03_why;

/**
 * Slide 3 — De ce excepții?
 * Valoarea specială de retur nu merge când orice valoare e validă.
 */
public class Demo01ReturnCodes {

    // Stil C: -1 înseamnă "eroare"... dar -1 e și un rezultat valid.
    static int successorC(int x) {
        if (x == Integer.MAX_VALUE) return -1;
        return x + 1;
    }

    // Stil Java: eroarea iese pe alt canal decât rezultatul.
    static int successor(int x) {
        return Math.addExact(x, 1); // aruncă ArithmeticException la overflow
    }

    public static void main(String[] args) {
        System.out.println("successorC(-2)        = " + successorC(-2));
        System.out.println("successorC(MAX_VALUE) = " + successorC(Integer.MAX_VALUE));
        System.out.println("  -> care dintre ele e eroare? Nu ai cum să știi.\n");

        System.out.println("fără verificare: MAX_VALUE + 1 = " + (Integer.MAX_VALUE + 1) + "  (overflow tăcut)");

        try {
            System.out.println("successor(41) = " + successor(41));
            System.out.println("successor(MAX_VALUE) = " + successor(Integer.MAX_VALUE));
            System.out.println("linia asta nu se mai execută");
        } catch (ArithmeticException e) {
            System.out.println("prins: " + e.getMessage());
        }
    }
}
