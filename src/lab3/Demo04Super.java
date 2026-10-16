package lab3;

/**
 * Slide 6 — super: constructorul părintelui, membri ascunși, metode suprascrise.
 */
public class Demo04Super {

    static class Parent {
        int value = 100;

        // Nu există constructor fără parametri!
        Parent(int x) {
            System.out.println("  Parent(" + x + ")");
        }

        void show() {
            System.out.println("  Parent.show(), value = " + value);
        }
    }

    static class Child extends Parent {
        int value = 200; // shadowing: ascunde câmpul din Parent

        Child() {
            super(10); // obligatoriu: super() implicit ar căuta Parent(), care nu există
            System.out.println("  Child()");
        }

        // DEMO EROARE: comentează constructorul de mai sus și decomentează-l pe acesta.
        // Mesaj: constructor Parent in class Parent cannot be applied to given types
        // Child() {
        //     System.out.println("  Child()");
        // }

        // Constructorul poate delega și către alt constructor din aceeași clasă.
        // În cazul ăsta compilatorul NU mai adaugă super() aici.
        Child(String label) {
            this();
            System.out.println("  Child(\"" + label + "\")");
        }

        @Override
        void show() {
            super.show(); // întâi comportamentul părintelui
            System.out.println("  Child.show(), value = " + value
                    + ", super.value = " + super.value);
        }
    }

    public static void main(String[] args) {
        System.out.println("== new Child() ==");
        Child c = new Child();

        System.out.println();
        System.out.println("== new Child(\"etichetă\") — this() apoi super(10) ==");
        new Child("etichetă");

        System.out.println();
        System.out.println("== show() cu super.show() și super.value ==");
        c.show();

        System.out.println();
        System.out.println("== Același obiect, două câmpuri value ==");
        Parent asParent = c;
        System.out.println("  ((Parent) c).value = " + asParent.value + "  <- câmpurile NU sunt polimorfice");
        System.out.println("  c.value            = " + c.value);

        // Despre „super() trebuie să fie prima instrucțiune”:
        // e adevărat până la JDK 24. Din JDK 25 (flexible constructor bodies) poți scrie
        // validări înainte de super(...), cât timp nu folosești this:
        //
        //   Child(int x) {
        //       if (x < 0) throw new IllegalArgumentException();
        //       super(x);
        //   }
        //
        // Proiectul e pe Java 21, deci varianta asta nu compilează aici.
    }
}
