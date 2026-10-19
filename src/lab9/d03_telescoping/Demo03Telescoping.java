package lab9.d03_telescoping;

/**
 * Slide 3 — De ce avem nevoie de Builder: constructori telescopici și setteri.
 */
public class Demo03Telescoping {

    // Varianta 1: constructori telescopici
    static class CoffeeT {
        final String size;   // obligatoriu
        final int shots;     // obligatoriu
        final int sugar;     // opțional
        final int milk;      // opțional
        final int syrup;     // opțional

        CoffeeT(String size, int shots) { this(size, shots, 0); }
        CoffeeT(String size, int shots, int sugar) { this(size, shots, sugar, 0); }
        CoffeeT(String size, int shots, int sugar, int milk) { this(size, shots, sugar, milk, 0); }
        CoffeeT(String size, int shots, int sugar, int milk, int syrup) {
            this.size = size;
            this.shots = shots;
            this.sugar = sugar;
            this.milk = milk;
            this.syrup = syrup;
        }

        @Override
        public String toString() {
            return size + ": shots=" + shots + ", sugar=" + sugar + ", milk=" + milk + ", syrup=" + syrup;
        }
    }

    // Varianta 2: constructor gol + setteri
    static class CoffeeS {
        private String size;
        private int shots;
        private int milk;

        void setSize(String size) { this.size = size; }
        void setShots(int shots) { this.shots = shots; }
        void setMilk(int milk) { this.milk = milk; }

        String describe() { return size.toUpperCase() + " cu " + shots + " shot(s), milk=" + milk; }
    }

    public static void main(String[] args) {
        // Vreau lapte, fără zahăr. Nu există constructor (size, shots, milk)...
        CoffeeT latte = new CoffeeT("large", 1, 0, 2);
        System.out.println("latte:      " + latte);

        // ...iar argumentele de același tip se inversează ușor. Compilează, dar e greșit:
        CoffeeT oops = new CoffeeT("large", 1, 2);   // am vrut milk=2, am pus sugar=2
        System.out.println("oops:       " + oops);

        // Cu setteri: obiectul există înainte să fie complet
        CoffeeS c = new CoffeeS();
        c.setShots(2);
        // uităm setSize(...)
        try {
            System.out.println(c.describe());
        } catch (NullPointerException e) {
            System.out.println("setteri:    obiect pe jumătate construit → NullPointerException");
        }

        // DEMO EROARE: decomentează → incompatible types: boolean cannot be converted to int (nu există varianta pe care o vrem)
        // CoffeeT iced = new CoffeeT("small", 1, true);
    }
}
