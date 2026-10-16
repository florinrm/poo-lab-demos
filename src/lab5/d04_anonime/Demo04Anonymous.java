package lab5.d04_anonime;

// Slides 6–7 — clase anonime: sintaxă, ce văd, ce nu pot face.

interface Greeter {
    String PREFIX = "Salut";                      // constantă (public static final)
    void greet(String name);
}

abstract class Animal {
    protected int age;
    Animal(int age) { this.age = age; }
    void speak() { System.out.println("Animal speaks"); }
    abstract void showDetails();
}

public class Demo04Anonymous {
    private String secret = "câmp privat al clasei externe";

    void run() {
        // 1. implementează o interfață; vede câmpul privat al clasei externe și constanta interfeței
        Greeter g = new Greeter() {
            @Override
            public void greet(String name) {
                System.out.println(PREFIX + ", " + name + "! (" + secret + ")");
            }
        };
        g.greet("Ana");

        // 2. extinde o clasă abstractă; argument trimis la constructorul părintelui + bloc de inițializare
        Animal a = new Animal(5) {
            {   // „constructorul” clasei anonime
                System.out.println("bloc de inițializare, age = " + age);
            }

            @Override
            void showDetails() {
                speak();                          // metodă moștenită
                System.out.println("Animal has age: " + age);
            }

            void extra() { System.out.println("metodă nouă"); }
        };
        a.showDetails();

        // DEMO EROARE: extra() nu există pe tipul Animal
        // a.extra();

        // 3. membri statici într-o clasă anonimă — permis din Java 16 (pagina spune că nu)
        Runnable counter = new Runnable() {
            static int calls = 0;
            static String label() { return "apel"; }
            public void run() { System.out.println(label() + " #" + (++calls)); }
        };
        counter.run();
        counter.run();

        // 4. variabile locale: doar final / effectively final
        int base = 10;
        Runnable r = new Runnable() {
            public void run() { System.out.println("base capturat = " + base); }
        };
        r.run();
        // DEMO EROARE: base nu mai e effectively final
        // base++;

        // trucul clasic: referința la array rămâne aceeași, conținutul se schimbă
        int[] clicks = {0};
        Runnable click = new Runnable() {
            public void run() { clicks[0]++; }
        };
        click.run(); click.run(); click.run();
        System.out.println("clicks[0] = " + clicks[0]);
    }

    public static void main(String[] args) {
        new Demo04Anonymous().run();
    }
}
