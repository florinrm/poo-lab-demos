package lab11.d12_primitive;

import lab11.data.Movie;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.IntStream;

/**
 * Slide 12 — IntStream / LongStream / DoubleStream: fără boxing, cu metode numerice.
 */
public class Demo12PrimitiveStreams {
    public static void main(String[] args) {
        List<Movie> movies = Movie.catalog();

        int total = movies.stream().mapToInt(Movie::minutes).sum();
        OptionalDouble avg = movies.stream().mapToDouble(Movie::rating).average();
        System.out.printf("total = %d min, nota medie = %.2f%n", total, avg.orElse(0));

        // Toate statisticile dintr-o singură trecere
        IntSummaryStatistics stats = movies.stream().mapToInt(Movie::year).summaryStatistics();
        System.out.println("ani: " + stats);

        // Pe un stream gol: sum = 0, dar average/max sunt Optional
        System.out.println("gol: sum=" + IntStream.empty().sum() + ", average=" + IntStream.empty().average()
                + ", max=" + IntStream.empty().max());

        // range / rangeClosed în loc de for
        System.out.println("pătrate 1..5: " + IntStream.rangeClosed(1, 5).map(i -> i * i).boxed().toList());
        System.out.println("indici pari < 10: " + IntStream.range(0, 10).filter(i -> i % 2 == 0).boxed().toList());

        // chars(): un String ca IntStream
        long vowels = "Programare Orientata pe Obiecte".toLowerCase().chars().filter(c -> "aeiou".indexOf(c) >= 0).count();
        System.out.println("vocale: " + vowels);

        // Costul boxing-ului
        List<Integer> big = IntStream.range(0, 5_000_000).boxed().toList();
        long t0 = System.nanoTime();
        int boxed = big.stream().reduce(0, Integer::sum);
        long t1 = System.nanoTime();
        int prim = big.stream().mapToInt(Integer::intValue).sum();
        long t2 = System.nanoTime();
        System.out.printf("reduce(Integer::sum): %d ms, mapToInt().sum(): %d ms (același rezultat: %b)%n",
                (t1 - t0) / 1_000_000, (t2 - t1) / 1_000_000, boxed == prim);

        // DEMO EROARE: decomentează → cannot find symbol: sum() — Stream<Integer> nu are sum(), IntStream are
        // int s = movies.stream().map(Movie::minutes).sum();
    }
}
