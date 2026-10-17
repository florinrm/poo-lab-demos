package lab7.s15_reading;

import lab7.DemoFiles;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

/**
 * Slide 15 — citire: Scanner (token-uri) vs. BufferedReader (linii).
 * Fișierul are un antet și apoi linii "oraș temperatură".
 */
public class Demo13Reading {

    public static void main(String[] args) throws IOException {
        File f = DemoFiles.file("temperaturi.txt");
        try (PrintWriter pw = new PrintWriter(f, StandardCharsets.UTF_8)) {
            pw.println("# oras temperatura");
            pw.println("Cluj 12.5");
            pw.println("Constanta 18.0");
            pw.println("Brasov -2.25");
        }

        System.out.println("--- Scanner: citește token cu token și parsează");
        try (Scanner sc = new Scanner(f, StandardCharsets.UTF_8)) {
            sc.useLocale(Locale.US);          // altfel, pe un sistem în română, se așteaptă "12,5"
            sc.nextLine();                    // sar antetul
            double sum = 0;
            int n = 0;
            while (sc.hasNext()) {
                String city = sc.next();
                double t = sc.nextDouble();
                System.out.println("  " + city + " -> " + t);
                sum += t;
                n++;
            }
            System.out.printf(Locale.US, "  media: %.2f%n", sum / n);
        }

        System.out.println("\n--- BufferedReader: citește linie cu linie; readLine() == null la EOF");
        try (BufferedReader br = new BufferedReader(new FileReader(f, StandardCharsets.UTF_8))) {
            String line;
            int nr = 0;
            while ((line = br.readLine()) != null) {     // fără excepție la final de fișier
                nr++;
                if (line.startsWith("#")) continue;
                String[] parts = line.split(" ");
                System.out.println("  linia " + nr + ": oraș=" + parts[0] + ", temp=" + parts[1]);
            }
        }

        System.out.println("\n--- fișier inexistent");
        try (BufferedReader br = new BufferedReader(new FileReader(DemoFiles.file("lipsa.txt")))) {
            br.readLine();
        } catch (FileNotFoundException e) {
            System.out.println("  FileNotFoundException: " + e.getMessage());
        }

        // Scanner pe System.in: NU îl închide (închide și System.in).
    }
}
