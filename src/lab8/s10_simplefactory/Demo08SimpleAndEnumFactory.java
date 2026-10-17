package lab8.s10_simplefactory;

import java.util.function.Supplier;

/**
 * Slide 10 — „factory după tip": Simple Factory (switch) și varianta cu enum.
 * Nu e Factory Method din GoF (acolo decid subclasele), dar e ce se folosește des în practică.
 */
public class Demo08SimpleAndEnumFactory {

    interface Drink { String serve(); }
    static class Espresso implements Drink { public String serve() { return "espresso scurt"; } }
    static class Latte    implements Drink { public String serve() { return "latte cu spumă"; } }
    static class Tea      implements Drink { public String serve() { return "ceai verde"; } }

    // 1. Simple Factory cu String: compilatorul nu te ajută dacă greșești numele
    static Drink fromString(String type) {
        return switch (type) {
            case "espresso" -> new Espresso();
            case "latte"    -> new Latte();
            case "tea"      -> new Tea();
            default -> throw new IllegalArgumentException("Băutură necunoscută: " + type);
        };
    }

    // 2. Factory prin enum: fiecare constantă știe să-și creeze produsul
    enum DrinkType {
        ESPRESSO(Espresso::new),
        LATTE(Latte::new),
        TEA(Tea::new);

        private final Supplier<Drink> maker;
        DrinkType(Supplier<Drink> maker) { this.maker = maker; }

        Drink create() { return maker.get(); }
    }

    // 3. Switch pe enum: compilatorul verifică exhaustivitatea (fără default)
    static int price(DrinkType t) {
        return switch (t) {
            case ESPRESSO -> 8;
            case LATTE -> 12;
            case TEA -> 7;
        };
    }

    public static void main(String[] args) {
        System.out.println("String:  " + fromString("latte").serve());
        try {
            fromString("Latte");                  // majusculă greșită — eroare abia la rulare
        } catch (IllegalArgumentException e) {
            System.out.println("String:  " + e.getMessage());
        }

        for (DrinkType t : DrinkType.values()) {
            System.out.println("Enum:    " + t + " -> " + t.create().serve() + ", " + price(t) + " lei");
        }

        System.out.println("Din input: " + DrinkType.valueOf("TEA").create().serve());

        // DEMO EROARE: adaugă o constantă nouă, MOCHA(Latte::new), în DrinkType ->
        // switch-ul din price() nu mai compilează: "the switch expression does not cover all possible input values"
    }
}
