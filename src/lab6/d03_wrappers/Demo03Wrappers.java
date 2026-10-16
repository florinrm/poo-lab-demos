package lab6.d03_wrappers;

/**
 * Slide 3 — Wrappers: creare, conversie, null ca "lipsă de valoare".
 */
public class Demo03Wrappers {
    public static void main(String[] args) {
        // Creare: valueOf / parseX (constructorii new Integer(...) sunt deprecated for removal)
        Integer a = Integer.valueOf(42);
        Integer b = Integer.valueOf("42");
        int c = Integer.parseInt("42");
        double d = Double.parseDouble("3.14");
        System.out.println(a + " " + b + " " + c + " " + d);

        // Conversii între forme primitive
        Double size = 32.76;
        System.out.println(size.intValue() + " " + size.longValue() + " " + size.floatValue());

        // Metode utilitare (statice!) pe wrappere
        System.out.println(Integer.toHexString(255) + " " + Integer.toBinaryString(10)
                + " " + Integer.MAX_VALUE + " " + Character.isDigit('7'));

        // null = "nu știu încă", fără valori magice de tip -1
        Integer numNodes = null;
        System.out.println(numNodes == null ? "nu am calculat încă" : "noduri: " + numNodes);

        // Imutabile: "a++" creează un obiect nou, nu modifică pe cel vechi
        Integer before = a;
        a++;
        System.out.println(before + " -> " + a + ", același obiect? " + (before == a));

        // String invalid → NumberFormatException (la runtime)
        try {
            Integer.parseInt("12abc");
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: " + e.getMessage());
        }

        // DEMO EROARE: decomentează → warning [removal]: constructorul e marcat pentru ștergere
        // Float pi = new Float(3.14);
    }
}
