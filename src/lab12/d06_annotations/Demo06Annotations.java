package lab12.d06_annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Slide 6 — Adnotări: built-in, custom, @Retention și @Target.
 */
public class Demo06Annotations {

    // RUNTIME: păstrată în bytecode ȘI vizibilă prin reflection
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    @interface Important {
        String value() default "";
        int priority() default 1;
        String[] tags() default {};
    }

    // Fără @Retention → implicit CLASS: în bytecode, dar NU la runtime
    @Target(ElementType.FIELD)
    @interface Draft { }

    @Important(value = "entitate de domeniu", tags = {"db", "api"})
    static class Gadget {
        @Important("cheie primară")
        @Draft
        private long id;

        @Important(value = "afișat în UI", priority = 2)
        private String name;

        private double price;

        @Deprecated(since = "2.0", forRemoval = true)
        void oldPrice() { }

        @Override
        public String toString() { return "Gadget#" + id; }
    }

    public static void main(String[] args) {
        Important onClass = Gadget.class.getAnnotation(Important.class);
        System.out.println("pe clasă: value=\"" + onClass.value() + "\", tags=" + Arrays.toString(onClass.tags())
                + ", priority=" + onClass.priority() + " (default)");

        for (Field f : Gadget.class.getDeclaredFields()) {
            Important imp = f.getAnnotation(Important.class);
            Draft draft = f.getAnnotation(Draft.class);
            System.out.printf("%-6s @Important=%-22s @Draft la runtime=%s%n",
                    f.getName(),
                    imp == null ? "-" : "\"" + imp.value() + "\"/" + imp.priority(),
                    draft == null ? "null (retenție CLASS)" : "da");
        }

        // Adnotările sunt și ele interfețe
        System.out.println("e interfață? " + Important.class.isInterface() + ", e adnotare? " + Important.class.isAnnotation());

        // DEMO EROARE: decomentează → method does not override or implement a method from a supertype
        // class Wrong { @Override public boolean equals(Wrong o) { return true; } }
    }
}
