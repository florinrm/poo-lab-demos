package lab11.d06_functional;

import lab11.data.Movie;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Slide 6 — Cele patru interfețe din java.util.function și cum se compun.
 */
public class Demo06FunctionalInterfaces {
    public static void main(String[] args) {
        Movie inception = Movie.catalog().get(0);
        Movie cars2 = Movie.catalog().get(9);

        // Predicate<T>: T → boolean
        Predicate<Movie> isSF = m -> m.genre().equals("SF");
        Predicate<Movie> isGood = m -> m.rating() >= 8;
        Predicate<Movie> goodSF = isSF.and(isGood);
        Predicate<Movie> weak = isGood.negate();
        System.out.println("goodSF(Inception)=" + goodSF.test(inception) + ", weak(Cars 2)=" + weak.test(cars2));
        System.out.println("isSF.or(isGood) pe Cars 2: " + isSF.or(isGood).test(cars2));

        // Function<T, R>: T → R, compunere cu andThen / compose
        Function<Movie, Integer> minutes = Movie::minutes;
        Function<Integer, String> hours = min -> (min / 60) + "h" + (min % 60) + "m";
        Function<Movie, String> duration = minutes.andThen(hours);      // întâi minutes, apoi hours
        System.out.println("durata: " + duration.apply(inception));

        Function<Integer, Integer> plusOne = x -> x + 1;
        Function<Integer, Integer> times2 = x -> x * 2;
        System.out.println("plusOne.andThen(times2)(5) = " + plusOne.andThen(times2).apply(5)
                + ", plusOne.compose(times2)(5) = " + plusOne.compose(times2).apply(5));

        // Consumer<T>: T → nimic (efect secundar)
        Consumer<String> print = s -> System.out.print("[" + s + "] ");
        Consumer<String> printLen = s -> System.out.print(s.length() + " ");
        List.of("a", "bb", "ccc").forEach(print.andThen(printLen));
        System.out.println();

        // Supplier<T>: nimic → T, evaluat abia la get()
        Supplier<String> expensive = () -> {
            System.out.println("   (calculez...)");
            return "rezultat";
        };
        System.out.println("supplier creat, încă nimic calculat");
        System.out.println(expensive.get());

        // Variante utile
        UnaryOperator<String> shout = s -> s.toUpperCase() + "!";           // Function<T, T>
        BinaryOperator<Integer> sum = Integer::sum;                          // BiFunction<T, T, T>
        BiFunction<String, Integer, String> repeat = String::repeat;
        System.out.println(shout.apply("hey") + " " + sum.apply(2, 3) + " " + repeat.apply("ab", 3));
        System.out.println("Predicate.not: " + List.of("", "x", " ").stream().filter(Predicate.not(String::isBlank)).toList());

        // DEMO EROARE: decomentează → bad return type in lambda expression: boolean cannot be converted to Integer
        // Function<String, Integer> f = s -> s.isEmpty();
    }
}
