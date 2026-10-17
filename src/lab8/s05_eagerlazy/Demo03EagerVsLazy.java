package lab8.s05_eagerlazy;

/**
 * Slide 5 — eager vs lazy. Când se creează, de fapt, instanța?
 *
 * Pagina spune că la eager instanța e creată „când e încărcată clasa, chiar dacă nu e folosită".
 * În Java câmpurile statice se inițializează la PRIMA FOLOSIRE ACTIVĂ a clasei
 * (apel de metodă statică, acces la câmp static, new), nu la pornirea programului.
 */
public class Demo03EagerVsLazy {

    static class EagerRegistry {
        private static final EagerRegistry INSTANCE = new EagerRegistry();

        private EagerRegistry() { System.out.println("   >> EagerRegistry creat"); }

        static EagerRegistry getInstance() { return INSTANCE; }

        static String version() { return "1.0"; }   // altă metodă statică
    }

    static class LazyRegistry {
        private static LazyRegistry instance;

        private LazyRegistry() { System.out.println("   >> LazyRegistry creat"); }

        static LazyRegistry getInstance() {
            if (instance == null) {
                instance = new LazyRegistry();
            }
            return instance;
        }

        static String version() { return "1.0"; }
    }

    public static void main(String[] args) {
        System.out.println("1. main a pornit — niciun registry creat încă");

        Class<?> c = EagerRegistry.class;
        System.out.println("2. am referit EagerRegistry.class — tot nimic");

        System.out.println("3. apelez EagerRegistry.version():");
        System.out.println("   version = " + EagerRegistry.version()
                + "   <- instanța eager s-a creat acum, deși n-am cerut-o");

        System.out.println("4. apelez LazyRegistry.version():");
        System.out.println("   version = " + LazyRegistry.version()
                + "   <- lazy: încă nimic");

        System.out.println("5. LazyRegistry.getInstance():");
        LazyRegistry.getInstance();
        LazyRegistry.getInstance();
        System.out.println("   (al doilea apel nu mai creează)");
    }
}
