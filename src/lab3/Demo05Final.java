package lab3;

import java.lang.reflect.Modifier;

/**
 * Slide 7 — final la clase și la metode.
 *
 * Decomentează pe rând liniile marcate DEMO EROARE.
 */
public class Demo05Final {

    static final class MathUtils {
        static int square(int x) { return x * x; }
    }

    // DEMO EROARE: cannot inherit from final MathUtils
    // static class MyMath extends MathUtils { }

    static class Animal {
        public final void die() { System.out.println("  Animal died."); }
        private void secret()   { System.out.println("  Animal.secret()"); }

        void callSecret() { secret(); }
    }

    static class Dog extends Animal {
        // DEMO EROARE: cannot override die() ... overridden method is final
        // @Override
        // public void die() { System.out.println("  Dog died."); }

        // Compilează, dar NU e suprascriere: secret() din Animal e private, deci invizibil aici.
        // Dacă pui @Override deasupra, primești eroare.
        private void secret() { System.out.println("  Dog.secret()"); }
    }

    public static void main(String[] args) {
        System.out.println("MathUtils.square(7) = " + MathUtils.square(7));

        System.out.println();
        System.out.println("== Metoda final se moștenește, dar nu se poate schimba ==");
        new Dog().die();

        System.out.println();
        System.out.println("== Metoda private nu e suprascrisă de Dog ==");
        new Dog().callSecret(); // afișează Animal.secret()

        System.out.println();
        System.out.println("== Clase din JDK care sunt final ==");
        for (Class<?> c : new Class<?>[] { String.class, Math.class, Integer.class, Object.class }) {
            System.out.println("  " + c.getSimpleName() + " final? " + Modifier.isFinal(c.getModifiers()));
        }
    }
}
