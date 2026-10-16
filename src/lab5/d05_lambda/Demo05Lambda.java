package lab5.d05_lambda;

// Slide 8 — aceeași interfață, implementată cu clasă anonimă și cu lambda.
// În IntelliJ: Alt+Enter pe „new Engine()” → Replace with lambda.

interface Engine {
    int getFuelCapacity();
}

interface Calculator {
    int apply(int a, int b);
}

public class Demo05Lambda {
    private final String name = "Demo05Lambda (obiectul exterior)";

    void compareThis() {
        Runnable anon = new Runnable() {
            public void run() { System.out.println("anonimă: this = " + this.getClass().getName()); }
        };
        Runnable lambda = () -> System.out.println("lambda:  this = " + this.name);
        anon.run();
        lambda.run();
    }

    public static void main(String[] args) {
        Engine anon = new Engine() {
            public int getFuelCapacity() { return 11; }
        };
        Engine lambda = () -> 11;
        System.out.println(anon.getFuelCapacity() + " == " + lambda.getFuelCapacity());

        Calculator add = (a, b) -> a + b;
        Calculator max = (a, b) -> {
            if (a > b) return a;
            return b;
        };
        System.out.println("add(2,3) = " + add.apply(2, 3) + ", max(2,3) = " + max.apply(2, 3));

        new Demo05Lambda().compareThis();

        // DEMO EROARE: lambda nu poate avea câmpuri
        // Engine withField = () -> { private int capacity = 11; return capacity; };
    }
}
