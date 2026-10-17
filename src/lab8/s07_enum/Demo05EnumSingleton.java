package lab8.s07_enum;

import java.lang.reflect.Constructor;

/**
 * Slide 7 — [Optional] Singleton cu enum.
 * 1. reflection „sparge" Singleton-ul clasic, dar nu și enum-ul;
 * 2. enum-ul NU e imutabil: poate avea stare modificabilă, ca orice Singleton.
 */
public class Demo05EnumSingleton {

    static class ClassicCounter {
        private static final ClassicCounter INSTANCE = new ClassicCounter();
        private ClassicCounter() { }
        static ClassicCounter getInstance() { return INSTANCE; }
    }

    enum VisitCounter {
        INSTANCE;

        private int visits;                       // stare modificabilă

        int visit() { return ++visits; }
    }

    public static void main(String[] args) throws Exception {
        // 1. reflection pe clasa clasică
        Constructor<ClassicCounter> k = ClassicCounter.class.getDeclaredConstructor();
        k.setAccessible(true);                    // ignoră private
        ClassicCounter fake = k.newInstance();
        System.out.println("Clasic: instanță nouă prin reflection? " + (fake != ClassicCounter.getInstance()));

        // 2. reflection pe enum
        try {
            Constructor<VisitCounter> ke = VisitCounter.class.getDeclaredConstructor(String.class, int.class);
            ke.setAccessible(true);
            ke.newInstance("COPY", 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Enum: " + e.getMessage());
        }

        // 3. „imutabil"? nu
        VisitCounter.INSTANCE.visit();
        VisitCounter.INSTANCE.visit();
        System.out.println("Enum, după două vizite: " + VisitCounter.INSTANCE.visit()
                + "   <- starea s-a schimbat");

        // DEMO EROARE: decomentează -> "enum classes may not be instantiated"
        // VisitCounter other = new VisitCounter();
    }
}
