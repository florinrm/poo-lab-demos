package lab8.s04_singleton;

/**
 * Slide 4 — structura: constructor privat, instanță statică, getInstance().
 */
public class Demo02Singleton {

    static class Clock {
        private static Clock instance;     // instanța unică, păstrată în clasă
        private long ticks;

        private Clock() {                  // nimeni din afară nu poate face new
            System.out.println("   Clock() — o singură dată");
        }

        public static Clock getInstance() {
            if (instance == null) {
                instance = new Clock();
            }
            return instance;
        }

        long tick() { return ++ticks; }
    }

    public static void main(String[] args) {
        Clock a = Clock.getInstance();
        Clock b = Clock.getInstance();

        System.out.println("a == b ? " + (a == b));
        System.out.println("a.tick() = " + a.tick());
        System.out.println("b.tick() = " + b.tick() + "   <- aceeași stare");

        // Clasele imbricate din același fișier văd membrii privați unele altora,
        // de aceea încercarea de new e făcută din OtherClass, o clasă separată.
        OtherClass.tryToCreate();
    }
}

class OtherClass {
    static void tryToCreate() {
        Demo02Singleton.Clock c = Demo02Singleton.Clock.getInstance();
        System.out.println("Din altă clasă primesc tot aceeași instanță, tick = " + c.tick());

        // DEMO EROARE: decomentează -> "Clock() has private access in Clock"
        // Demo02Singleton.Clock d = new Demo02Singleton.Clock();
    }
}
