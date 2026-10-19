package lab12.d08_miniframework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Slide 8 — Adnotări + reflection = cum funcționează JUnit și validatoarele.
 * Scriem în 60 de linii un mini test runner și un mini validator.
 */
public class Demo08MiniFramework {

    // ---------- 1. Un "JUnit" minuscul ----------
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Check {
        String value() default "";
    }

    static class MathChecks {
        @Check("2 + 2 = 4")
        void addition() { expect(2 + 2 == 4); }

        @Check("împărțirea întreagă rotunjește spre zero")
        void division() { expect(-7 / 2 == -3); }

        @Check("0.1 + 0.2 == 0.3")
        void floating() { expect(0.1 + 0.2 == 0.3); }      // pică intenționat

        void helper() { }                                    // fără @Check: ignorat

        static void expect(boolean ok) { if (!ok) throw new AssertionError("expected true"); }
    }

    static void runChecks(Class<?> cls) throws Exception {
        Object instance = cls.getDeclaredConstructor().newInstance();
        int passed = 0, failed = 0;
        for (Method m : cls.getDeclaredMethods()) {
            Check check = m.getAnnotation(Check.class);
            if (check == null) continue;
            try {
                m.invoke(instance);
                passed++;
                System.out.println("   ✔ " + check.value());
            } catch (InvocationTargetException e) {          // excepția metodei vine împachetată
                failed++;
                System.out.println("   ✘ " + check.value() + " → " + e.getCause().getClass().getSimpleName());
            }
        }
        System.out.println("   " + passed + " trecute, " + failed + " picate");
    }

    // ---------- 2. Un validator ----------
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface NotBlank { }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Range {
        int min();
        int max();
    }

    static class Registration {
        @NotBlank String name;
        @Range(min = 1, max = 4) int year;
        @Range(min = 1, max = 10) int grade;

        Registration(String name, int year, int grade) { this.name = name; this.year = year; this.grade = grade; }
    }

    static List<String> validate(Object o) throws IllegalAccessException {
        List<String> errors = new ArrayList<>();
        for (Field f : o.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            Object value = f.get(o);
            if (f.isAnnotationPresent(NotBlank.class) && (value == null || value.toString().isBlank())) {
                errors.add(f.getName() + " nu poate fi gol");
            }
            Range r = f.getAnnotation(Range.class);
            if (r != null && value instanceof Integer i && (i < r.min() || i > r.max())) {
                errors.add(f.getName() + "=" + i + " nu e în [" + r.min() + ", " + r.max() + "]");
            }
        }
        return errors;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("mini test runner:");
        runChecks(MathChecks.class);

        System.out.println("validator:");
        System.out.println("   corect:   " + validate(new Registration("Ana", 2, 9)));
        System.out.println("   greșit:   " + validate(new Registration(" ", 6, 0)));

        // DEMO EROARE: decomentează → annotation @Range is missing default values for elements min,max
        // class Bad { @Range int x; }
    }
}
