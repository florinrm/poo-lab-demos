package lab8.s11_abstractfactory;

/**
 * Slide 11 — Abstract Factory: familii de obiecte care trebuie să se potrivească.
 * O temă (Light / Dark) produce un set coerent: buton + câmp text + bară de titlu.
 */
public class Demo09AbstractFactory {

    // Produse abstracte
    interface Button    { String draw(String label); }
    interface TextField { String draw(String hint); }
    interface TitleBar  { String draw(String title); }

    // Familia Light
    static class LightButton    implements Button    { public String draw(String l) { return "[ " + l + " ]"; } }
    static class LightTextField implements TextField { public String draw(String h) { return "|_" + h + "____|"; } }
    static class LightTitleBar  implements TitleBar  { public String draw(String t) { return "=== " + t + " ==="; } }

    // Familia Dark
    static class DarkButton    implements Button    { public String draw(String l) { return "▐█ " + l + " █▌"; } }
    static class DarkTextField implements TextField { public String draw(String h) { return "▓▓" + h + "▓▓▓▓"; } }
    static class DarkTitleBar  implements TitleBar  { public String draw(String t) { return "███ " + t + " ███"; } }

    // Fabrica abstractă: câte o metodă de creare pentru fiecare produs
    interface ThemeFactory {
        Button createButton();
        TextField createTextField();
        TitleBar createTitleBar();
    }

    static class LightTheme implements ThemeFactory {
        public Button createButton()       { return new LightButton(); }
        public TextField createTextField() { return new LightTextField(); }
        public TitleBar createTitleBar()   { return new LightTitleBar(); }
    }

    static class DarkTheme implements ThemeFactory {
        public Button createButton()       { return new DarkButton(); }
        public TextField createTextField() { return new DarkTextField(); }
        public TitleBar createTitleBar()   { return new DarkTitleBar(); }
    }

    // Clientul: nu apare niciun nume de clasă concretă
    static class LoginForm {
        private final Button ok;
        private final TextField user;
        private final TitleBar bar;

        LoginForm(ThemeFactory f) {
            bar = f.createTitleBar();
            user = f.createTextField();
            ok = f.createButton();
        }

        void render() {
            System.out.println(bar.draw("Autentificare"));
            System.out.println(user.draw("utilizator"));
            System.out.println(ok.draw("OK"));
        }
    }

    public static void main(String[] args) {
        String choice = args.length > 0 ? args[0] : "dark";      // ex. din setările utilizatorului
        ThemeFactory theme = choice.equals("light") ? new LightTheme() : new DarkTheme();

        new LoginForm(theme).render();
        System.out.println();
        new LoginForm(new LightTheme()).render();

        // Fără fabrică, nimic nu te oprește să amesteci familiile:
        System.out.println("\nAmestecat de mână: " + new LightTitleBar().draw("Mix") + "  " + new DarkButton().draw("OK"));
        System.out.println("Cu o fabrică, toate piesele vin din aceeași familie.");
    }
}
