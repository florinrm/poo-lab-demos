package lab7.s04_trycatch;

/**
 * Slide 4 — throw / try / catch.
 * Ce se abandonează când apare o excepție și unde continuă programul.
 */
public class Demo02TryCatch {

    static int parsePort(String s) {
        int port = Integer.parseInt(s);           // poate arunca NumberFormatException
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("port în afara intervalului: " + port);
        }
        return port;
    }

    public static void main(String[] args) {
        String[] inputs = {"8080", "abc", "70000"};

        for (String in : inputs) {
            try {
                System.out.println("1. încerc \"" + in + "\"");
                int p = parsePort(in);
                System.out.println("2. port valid: " + p);
            } catch (NumberFormatException e) {
                System.out.println("   nu e număr -> " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("   număr invalid -> " + e.getMessage());
            }
            System.out.println("3. după try/catch, programul merge mai departe\n");
        }

        // try imbricat: o operație opțională nu strică secvența mare
        try {
            System.out.println("op1");
            try {
                System.out.println("op2 (opțională)");
                Object o = null;
                o.toString();                      // NPE
                System.out.println("op3 — sărită");
            } catch (NullPointerException e) {
                System.out.println("catch interior: am ignorat eșecul lui op2");
            }
            System.out.println("op4 — se execută");
        } catch (Exception e) {
            System.out.println("catch exterior — nu ajungem aici");
        }

        // Observație: NumberFormatException e unchecked, dar e perfect normal să o prinzi
        // când parsezi input de la utilizator.
    }
}
