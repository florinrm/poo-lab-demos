package lab7.s16_writing;

import lab7.DemoFiles;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Slide 16 — scriere: suprascriere vs. append, buffer + flush, PrintWriter care tace.
 */
public class Demo14Writing {

    public static void main(String[] args) throws IOException {
        File log = DemoFiles.file("jurnal.txt");

        // 1) suprascriere (implicit)
        for (int run = 1; run <= 3; run++) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(log, StandardCharsets.UTF_8))) {
                bw.write("rulare " + run);
                bw.newLine();
            }
        }
        System.out.println("fără append:\n" + content(log));

        // 2) append = true
        for (int run = 1; run <= 3; run++) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(log, StandardCharsets.UTF_8, true))) {
                bw.write("rulare " + run);
                bw.newLine();
            }
        }
        System.out.println("cu append:\n" + content(log));

        // 3) buffer fără flush/close: datele stau în memorie
        File lost = DemoFiles.file("pierdut.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(lost, StandardCharsets.UTF_8));
        bw.write("date importante");
        System.out.println("înainte de flush, pe disc: " + lost.length() + " octeți");
        bw.flush();
        System.out.println("după flush, pe disc:       " + lost.length() + " octeți");
        bw.close();

        // 4) PrintWriter nu aruncă IOException la scriere — trebuie întrebat cu checkError()
        PrintWriter pw = new PrintWriter(new FileWriter(DemoFiles.file("raport.txt"), StandardCharsets.UTF_8));
        pw.close();
        pw.println("scriu după close");                 // nicio excepție!
        System.out.println("\nPrintWriter după close: checkError() = " + pw.checkError());

        // 5) printf: %n pune separatorul sistemului; "\n" pune mereu LF
        try (PrintWriter rep = new PrintWriter(DemoFiles.file("raport.txt"), StandardCharsets.UTF_8)) {
            rep.printf(java.util.Locale.US, "Valoare X: %d, Valoare Y: %.2f%n", 10, 12.345);
        }
        System.out.print("raport.txt: " + content(DemoFiles.file("raport.txt")));
    }

    static String content(File f) throws IOException {
        return Files.readString(f.toPath(), StandardCharsets.UTF_8);
    }
}
