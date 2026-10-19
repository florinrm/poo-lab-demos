package lab9.d04_builder;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.stream.Stream;

/**
 * Slide 4 — Builder-ul simplu (clasă internă statică), varianta pe care o veți folosi.
 */
public class Demo04Builder {
    public static void main(String[] args) {
        Coffee latte = new Coffee.Builder("large", 1)
                .milk(2)
                .build();
        System.out.println("latte:      " + latte);

        // Ordinea apelurilor opționale nu contează; numele spun ce setăm
        Coffee frappe = new Coffee.Builder("medium", 2)
                .iced()
                .sugar(1)
                .milk(1)
                .build();
        System.out.println("frappe:     " + frappe);

        // "Copie cu o modificare" — originalul rămâne neschimbat (e imutabil)
        Coffee sweetLatte = latte.toBuilder().sugar(2).build();
        System.out.println("sweetLatte: " + sweetLatte);
        System.out.println("latte:      " + latte + "  ← neschimbat");

        // Validare: parametru invalid vs. combinație invalidă
        try {
            new Coffee.Builder("small", 1).sugar(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("sugar(-1)            → IllegalArgumentException: " + e.getMessage());
        }
        try {
            new Coffee.Builder("small", 1).iced().extraHot().build();
        } catch (IllegalStateException e) {
            System.out.println("iced + extraHot      → IllegalStateException: " + e.getMessage());
        }

        // Builder în JDK
        String s = new StringBuilder().append("a").append(1).append('b').toString();
        Stream<String> st = Stream.<String>builder().add("x").add("y").build();
        HttpRequest req = HttpRequest.newBuilder(URI.create("https://ocw.cs.pub.ro"))
                .timeout(Duration.ofSeconds(5))
                .header("Accept", "text/html")
                .GET()
                .build();
        System.out.println("JDK: " + s + ", " + st.toList() + ", " + req.method() + " " + req.uri());

        // DEMO EROARE: decomentează → incompatible types: Coffee.Builder cannot be converted to Coffee (lipsește build())
        // Coffee forgot = new Coffee.Builder("small", 1).milk(1);
    }
}
