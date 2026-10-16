package lab2.s05_methods;

/**
 * Slide 5: variabile locale, shadowing, supraîncărcare.
 */
class Apple {
    int x = 2;
    int y = 3;

    // Parametrii x și y ascund (shadow) câmpurile x și y
    void moveToBroken(int x, int y) {
        x = x;   // atribuie parametrul lui însuși — câmpul nu se schimbă!
        y = y;
    }

    void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

class Calculator {
    int sum(int a, int b) {
        System.out.print("[int,int] ");
        return a + b;
    }

    double sum(double a, double b) {
        System.out.print("[double,double] ");
        return a + b;
    }

    int sum(int a, int b, int c) {
        System.out.print("[int,int,int] ");
        return a + b + c;
    }

    long twice(long v) {
        System.out.print("[long] ");
        return 2 * v;
    }
}

public class MethodsDemo {
    public static void main(String[] args) {
        // --- Shadowing ---
        Apple a = new Apple();
        a.moveToBroken(10, 11);
        System.out.println("după moveToBroken: " + a);   // tot (2, 3)
        a.moveTo(10, 11);
        System.out.println("după moveTo:       " + a);   // (10, 11)

        // --- Overloading: alegerea se face la COMPILARE, după semnătură ---
        Calculator calc = new Calculator();
        System.out.println(calc.sum(2, 3));
        System.out.println(calc.sum(2.5, 3.7));
        System.out.println(calc.sum(1, 2, 3));
        System.out.println(calc.sum(2, 3.5));   // int → double prin conversie
        System.out.println(calc.twice(21));     // int → long prin conversie

        // Exemplu cunoscut: print are zeci de variante
        System.out.println(232.4f);
        System.out.println(true);

        // --- Inițializarea sigură a variabilelor locale ---
        boolean cond = args.length > 0;
        int bar;
        if (cond) {
            bar = 42;
        } else {
            bar = 0;     // ștergeți ramura else → eroare de compilare la bar++
        }
        bar++;
        System.out.println("bar = " + bar);
    }
}
