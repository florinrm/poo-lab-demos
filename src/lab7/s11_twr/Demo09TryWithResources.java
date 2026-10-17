package lab7.s11_twr;

/**
 * Slide 11 — try-with-resources.
 * Ordinea de închidere, închidere și la excepție, excepții suprimate.
 */
public class Demo09TryWithResources {

    static class Resource implements AutoCloseable {
        private final String name;
        private final boolean failOnClose;

        Resource(String name, boolean failOnClose) {
            this.name = name;
            this.failOnClose = failOnClose;
            System.out.println("  deschid " + name);
        }

        void use() { System.out.println("  folosesc " + name); }

        @Override
        public void close() {
            System.out.println("  închid " + name);
            if (failOnClose) throw new IllegalStateException("close() a eșuat pe " + name);
        }
    }

    public static void main(String[] args) {
        System.out.println("1) caz normal — închidere în ordine INVERSĂ deschiderii");
        try (Resource a = new Resource("A", false);
             Resource b = new Resource("B", false)) {
            a.use();
            b.use();
        }

        System.out.println("\n2) excepție în corp — resursele se închid ÎNAINTE de catch");
        try (Resource a = new Resource("A", false)) {
            a.use();
            throw new RuntimeException("eroare în corp");
        } catch (RuntimeException e) {
            System.out.println("  catch: " + e.getMessage());
        }

        System.out.println("\n3) eșuează și corpul, și close() — nu se pierde nimic");
        try (Resource a = new Resource("A", true)) {
            throw new RuntimeException("eroarea principală");
        } catch (RuntimeException e) {
            System.out.println("  principală: " + e.getMessage());
            for (Throwable s : e.getSuppressed()) {
                System.out.println("  suprimată:  " + s.getMessage());
            }
        }

        System.out.println("\n4) varianta veche cu finally — excepția din close() o ASCUNDE pe cea principală");
        try {
            oldStyle();
        } catch (RuntimeException e) {
            System.out.println("  apelantul vede doar: " + e.getMessage());
            System.out.println("  suprimate: " + e.getSuppressed().length + "  -> \"eroarea principală\" s-a pierdut");
        }
    }

    static void oldStyle() {
        Resource r = null;
        try {
            r = new Resource("A", true);
            throw new RuntimeException("eroarea principală");
        } finally {
            if (r != null) r.close();   // aruncă și înlocuiește excepția din try
        }
    }
}
