package lab11.d04_lambda;

/**
 * Slide 4 — Interfețe funcționale: de la clasă anonimă la lambda.
 */
public class Demo04Lambda {

    // O singură metodă abstractă → interfață funcțională
    @FunctionalInterface
    interface TextRule {
        boolean accepts(String text);

        // metodele default/static nu contează la "o singură metodă abstractă"
        default TextRule and(TextRule other) {
            return t -> accepts(t) && other.accepts(t);
        }

        static TextRule minLength(int n) {
            return t -> t.length() >= n;
        }
    }

    @FunctionalInterface
    interface Operation {
        int apply(int a, int b);
    }

    public static void main(String[] args) {
        // 1. Clasă anonimă
        TextRule noSpaces = new TextRule() {
            @Override
            public boolean accepts(String text) {
                return !text.contains(" ");
            }
        };

        // 2. Lambda: aceeași implementare
        TextRule noSpaces2 = text -> !text.contains(" ");

        // 3. Lambda cu bloc
        TextRule hasDigit = text -> {
            for (char c : text.toCharArray()) {
                if (Character.isDigit(c)) return true;
            }
            return false;
        };

        TextRule password = TextRule.minLength(8).and(noSpaces2).and(hasDigit);
        for (String p : new String[]{"parola", "parola lunga 1", "javaRules25"}) {
            System.out.printf("%-16s anonim=%b lambda=%b parolă validă=%b%n",
                    p, noSpaces.accepts(p), noSpaces2.accepts(p), password.accepts(p));
        }

        // Aceeași formă (int, int) -> int, comportamente diferite
        Operation add = (a, b) -> a + b;
        Operation max = (a, b) -> a > b ? a : b;
        Operation pow = (base, exp) -> {
            int r = 1;
            for (int i = 0; i < exp; i++) r *= base;
            return r;
        };
        System.out.println("add=" + add.apply(2, 3) + " max=" + max.apply(2, 3) + " pow=" + pow.apply(2, 10));

        // O lambda e un OBIECT care implementează interfața
        System.out.println("add e un obiect: " + (add instanceof Operation) + ", clasa: " + add.getClass().getSimpleName());

        // DEMO EROARE: decomentează → Unexpected @FunctionalInterface annotation: Bad nu e interfață funcțională (2 metode abstracte)
        // @FunctionalInterface interface Bad { void a(); void b(); }
    }
}
