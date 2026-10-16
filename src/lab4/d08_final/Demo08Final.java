package lab4.d08_final;

import java.util.ArrayList;
import java.util.List;

/**
 * Slide 12: final pe camp, variabila locala, parametru.
 *
 * Ideea-cheie: final blocheaza REFERINTA, nu OBIECTUL.
 */
class Human {
    String job = "unemployed";
    int health = 100;
}

class Alien {
    public static final int RAY_DAMAGE = 70; // constanta "globala"
    private final String name;               // camp final: setat o data, in constructor

    Alien(String name) {
        this.name = name;
    }

    public String sayHelloToHumans() {
        final String evilMessage = " prepare your world to be conquered!";
        final String goodMessage = " we want to taste shaorma!";
        if (name.equals("Good Boy Roy")) {
            return "My name is " + name + goodMessage;
        }
        // pe pagina scrie "badMessage" -> variabila nu exista, nu compileaza
        return "My name is " + name + evilMessage;
    }

    public void shootRayGun(final Human human) {
        human.health -= RAY_DAMAGE;   // OK: modificam obiectul
        human.job = "plumber";        // OK: tot obiectul
        // DEMO EROARE: reatribuim referinta finala
        // human = new Human();
        System.out.println("  Resistance is futile! (health=" + human.health + ", job=" + human.job + ")");
    }

    // DEMO EROARE: campul final nu mai poate fi schimbat
    // public void rename(String n) { this.name = n; }
}

public class Demo08Final {
    public static void main(String[] args) {
        System.out.println(new Alien("Good Boy Roy").sayHelloToHumans());
        System.out.println(new Alien("Zorg").sayHelloToHumans());

        Human h = new Human();
        new Alien("Zorg").shootRayGun(h);
        System.out.println("Dupa apel, in main: health=" + h.health + ", job=" + h.job);

        System.out.println("\n== final nu inseamna imutabil ==");
        final List<String> crew = new ArrayList<>();
        crew.add("Ripley");           // OK
        crew.add("Dallas");
        System.out.println("crew = " + crew);
        // crew = new ArrayList<>();  // DEMO EROARE

        final int[] codes = {1, 2, 3};
        codes[0] = 99;                // OK: continutul array-ului se schimba
        System.out.println("codes[0] = " + codes[0]);
    }
}
