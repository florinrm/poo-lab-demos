package lab9.d09_strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Slide 9–10 — Strategy: un navigator care calculează timpul de deplasare.
 */
public class Demo09Strategy {

    // Strategy: o singură metodă abstractă → strategiile pot fi și lambda-uri
    interface RouteStrategy {
        double minutes(double km);
    }

    static class Car implements RouteStrategy {
        public double minutes(double km) { return km / 40 * 60 + 5; }      // 40 km/h în oraș + parcare
    }

    static class Bike implements RouteStrategy {
        public double minutes(double km) { return km / 15 * 60; }
    }

    static class Walk implements RouteStrategy {
        public double minutes(double km) { return km / 5 * 60; }
    }

    // Contextul: folosește o strategie, o poate schimba la runtime, nu are niciun if pe tip
    static class Navigator {
        private RouteStrategy strategy;

        Navigator(RouteStrategy strategy) { this.strategy = strategy; }
        void setStrategy(RouteStrategy strategy) { this.strategy = strategy; }

        String eta(double km) {
            return String.format("%.1f km → %.0f min", km, strategy.minutes(km));
        }
    }

    // Aceeași idee cu enum: fiecare constantă își implementează strategia
    enum Mode implements RouteStrategy {
        SCOOTER { public double minutes(double km) { return km / 20 * 60; } },
        METRO   { public double minutes(double km) { return 8 + km * 1.5; } }
    }

    // Două metode abstracte: NU mai e interfață funcțională
    interface NamedStrategy {
        double minutes(double km);
        String name();
    }

    public static void main(String[] args) {
        Navigator nav = new Navigator(new Car());
        System.out.println("mașină:      " + nav.eta(6));

        nav.setStrategy(new Bike());                 // schimbăm comportamentul, nu clasa
        System.out.println("bicicletă:   " + nav.eta(6));

        nav.setStrategy(new Walk());
        System.out.println("pe jos:      " + nav.eta(6));

        // Strategie nouă fără clasă nouă și fără să modificăm Navigator
        nav.setStrategy(km -> km / 10 * 60 + 2);
        System.out.println("trotinetă:   " + nav.eta(6));

        for (Mode m : Mode.values()) {
            nav.setStrategy(m);
            System.out.printf("%-12s %s%n", m.name().toLowerCase() + ":", nav.eta(6));
        }

        // Strategy în JDK: Comparator e o strategie de comparare
        List<String> cities = new ArrayList<>(List.of("Iasi", "Bucuresti", "Arad", "Cluj-Napoca"));
        cities.sort(Comparator.naturalOrder());
        System.out.println("alfabetic:   " + cities);
        cities.sort(Comparator.comparing(String::length));
        System.out.println("după lungime:" + cities);

        // DEMO EROARE: decomentează → incompatible types: NamedStrategy is not a functional interface
        // NamedStrategy ns = km -> km * 2;
    }
}
