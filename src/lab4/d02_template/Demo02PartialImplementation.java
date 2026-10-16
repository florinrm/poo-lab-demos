package lab4.d02_template;

/**
 * Slide 5: cand alegem o clasa abstracta.
 *
 * Clasa abstracta = implementare PARTIALA: codul comun (perform) sta in parinte,
 * detaliile (play/stop) le scriu copiii. Parintele apeleaza metode pe care inca nu le are!
 * (Idee care revine la Design Patterns: Template Method.)
 *
 * Bonus: o clasa abstracta FARA metode abstracte - doar ca sa interzica "new".
 */
abstract class Instrument {
    private final String name;

    protected Instrument(String name) {
        this.name = name;
    }

    // cod comun, scris o singura data
    public final void perform() {
        System.out.println("-- " + name + " intra pe scena --");
        play();
        stop();
        System.out.println("-- aplauze --");
    }

    protected abstract void play();

    protected abstract void stop();
}

class Guitar extends Instrument {
    Guitar() {
        super("Chitara");
    }

    @Override
    protected void play() {
        System.out.println("  strum strum");
    }

    @Override
    protected void stop() {
        System.out.println("  palma pe corzi");
    }
}

class Saxophone extends Instrument {
    Saxophone() {
        super("Saxofon");
    }

    @Override
    protected void play() {
        System.out.println("  tuuu-tu-tuuu");
    }

    @Override
    protected void stop() {
        System.out.println("  respiratie adanca");
    }
}

/** Abstracta fara metode abstracte: valid, doar ca nu mai poti face new Pet(). */
abstract class Pet {
    private double weight;

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}

class Hamster extends Pet {
}

public class Demo02PartialImplementation {
    public static void main(String[] args) {
        Instrument[] band = { new Guitar(), new Saxophone() };
        for (Instrument i : band) {
            i.perform();
        }

        Pet p = new Hamster();
        p.setWeight(0.12);
        System.out.println("\nHamster: " + p.getWeight() + " kg");

        // DEMO EROARE: Pet nu are nimic abstract, dar tot nu se poate instantia
        // Pet generic = new Pet();
    }
}
