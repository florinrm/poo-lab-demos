package lab5.d12_builder;

// Slide 16 — String += în buclă vs. StringBuilder.append.

public class Demo12StringBuilder {
    static final int N = 50_000;

    public static void main(String[] args) {
        long t0 = System.nanoTime();
        String s = "";
        for (int i = 0; i < N; i++) {
            s += "x";                             // copiază tot șirul la fiecare pas
        }
        long t1 = System.nanoTime();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            sb.append("x");                       // același buffer
        }
        String r = sb.toString();
        long t2 = System.nanoTime();

        System.out.printf("String +=       : %6d ms (lungime %d)%n", (t1 - t0) / 1_000_000, s.length());
        System.out.printf("StringBuilder   : %6d ms (lungime %d)%n", (t2 - t1) / 1_000_000, r.length());

        StringBuilder word = new StringBuilder("laborator");
        word.reverse();
        System.out.println("reverse:      " + word);   // același obiect, modificat
        word.reverse().insert(0, "[").append("]");
        System.out.println("insert/append: " + word);
        word.deleteCharAt(0);
        System.out.println("deleteCharAt: " + word);

        System.out.println("repeat: " + "ab".repeat(3));
    }
}
