package lab12.d07_reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Slide 7 — Reflection: inspectăm și folosim o clasă la runtime, după nume.
 */
public class Demo07Reflection {

    public static void main(String[] args) throws Exception {
        // 1. Trei moduri de a obține obiectul Class
        Class<?> c1 = Account.class;
        Class<?> c2 = new Account().getClass();
        Class<?> c3 = Class.forName("lab12.d07_reflection.Account");
        System.out.println("aceeași clasă: " + (c1 == c2 && c2 == c3) + " → " + c1.getSimpleName());

        // 2. Structura
        for (Field f : c1.getDeclaredFields()) {
            System.out.println("   câmp:   " + Modifier.toString(f.getModifiers()) + " " + f.getType().getSimpleName() + " " + f.getName());
        }
        for (Method m : c1.getDeclaredMethods()) {
            System.out.println("   metodă: " + Modifier.toString(m.getModifiers()) + " " + m.getName() + "(" + m.getParameterCount() + " param)");
        }

        // 3. Obiect creat după nume și metodă apelată după nume (ex. citite dintr-un fișier de config)
        String className = "lab12.d07_reflection.Account";
        String action = "deposit";
        Constructor<?> ctor = Class.forName(className).getConstructor(String.class, double.class);
        Object acc = ctor.newInstance("Ana", 100.0);
        Method m = acc.getClass().getMethod(action, double.class);
        m.invoke(acc, 50.0);
        System.out.println("după invoke(\"deposit\", 50): " + acc);

        // 4. Ocolim încapsularea: câmp privat și metodă privată
        Field balance = Account.class.getDeclaredField("balance");
        balance.setAccessible(true);
        balance.set(acc, 1_000_000.0);
        Method secret = Account.class.getDeclaredMethod("secret");
        secret.setAccessible(true);
        System.out.println("după set pe câmpul privat: " + acc + ", secret(): " + secret.invoke(acc));

        // 5. ...dar nu și în interiorul JDK-ului: sistemul de module (Java 9+) blochează
        try {
            Field value = String.class.getDeclaredField("value");
            value.setAccessible(true);
        } catch (InaccessibleObjectException e) {
            System.out.println("String.value → InaccessibleObjectException (java.base nu e deschis)");
        }

        // 6. Erorile se mută la runtime: un typo în nume nu e prins de compilator
        try {
            acc.getClass().getMethod("deposti", double.class);
        } catch (NoSuchMethodException e) {
            System.out.println("typo \"deposti\" → NoSuchMethodException, abia la rulare");
        }

        // DEMO EROARE: decomentează → compilează, dar IllegalAccessException la runtime: metodă privată fără setAccessible(true)
        // Account.class.getDeclaredMethod("secret").invoke(new Account());
    }
}
