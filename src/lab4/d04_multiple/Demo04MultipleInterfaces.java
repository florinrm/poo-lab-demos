package lab4.d04_multiple;

/**
 * Slide 7: extindere de interfete si "mostenire multipla".
 *
 * De aratat:
 *  - o interfata poate extinde MAI MULTE interfete (Duck extends Flyer, Swimmer);
 *  - o clasa poate implementa mai multe interfete, dar extinde o singura clasa;
 *  - acelasi obiect poate fi privit prin mai multe "ecusoane".
 */
interface Flyer {
    void fly();
}

interface Swimmer {
    void swim();
}

interface Duck extends Flyer, Swimmer {
    void quack();
}

class Mallard implements Duck {
    @Override
    public void fly() {
        System.out.println("  Mallard: zbor!");
    }

    @Override
    public void swim() {
        System.out.println("  Mallard: inot!");
    }

    @Override
    public void quack() {
        System.out.println("  Mallard: mac!");
    }
}

class Drone implements Flyer {
    @Override
    public void fly() {
        System.out.println("  Drone: bzzzz");
    }
}

class Submarine implements Swimmer {
    @Override
    public void swim() {
        System.out.println("  Submarine: blub");
    }
}

public class Demo04MultipleInterfaces {
    public static void main(String[] args) {
        Mallard m = new Mallard();

        // acelasi obiect, trei "ecusoane"
        Flyer f = m;
        Swimmer s = m;
        Duck d = m;
        f.fly();
        s.swim();
        d.quack();

        System.out.println("\nTot ce zboara, indiferent de ierarhie:");
        Flyer[] flyers = { new Mallard(), new Drone() };
        for (Flyer x : flyers) {
            x.fly();
        }

        System.out.println("\nTot ce inoata:");
        Swimmer[] swimmers = { new Mallard(), new Submarine() };
        for (Swimmer x : swimmers) {
            x.swim();
        }

        // DEMO EROARE: prin referinta Flyer vezi doar ce promite Flyer
        // f.swim();
    }
}
