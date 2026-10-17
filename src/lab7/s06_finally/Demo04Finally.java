package lab7.s06_finally;

/**
 * Slide 6 — finally.
 * Rulează mereu (aproape): după try, după catch, chiar și înainte de return.
 */
public class Demo04Finally {

    static int fromTry() {
        try {
            System.out.println("  try: return 1");
            return 1;
        } finally {
            System.out.println("  finally rulează înainte ca metoda să se întoarcă");
        }
    }

    static int counter() {
        int x = 10;
        try {
            return x;          // valoarea 10 e "fotografiată" acum
        } finally {
            x = 99;            // nu schimbă ce se întoarce
        }
    }

    @SuppressWarnings("finally")
    static int returnInFinally() {
        try {
            throw new IllegalStateException("excepție importantă");
        } finally {
            return -1;         // CAPCANĂ: înghite excepția și suprascrie orice return
        }
    }

    public static void main(String[] args) {
        System.out.println("fromTry() = " + fromTry());
        System.out.println("counter() = " + counter() + "  (nu 99)");
        System.out.println("returnInFinally() = " + returnInFinally() + "  (excepția a dispărut!)");

        try {
            System.out.println("\nîn try, apoi System.exit(0)");
            if (args.length > 0 && args[0].equals("exit")) {
                System.exit(0);   // rulează cu argumentul "exit": finally NU mai apare
            }
        } finally {
            System.out.println("finally (sare peste el doar System.exit / JVM oprit)");
        }
    }
}
