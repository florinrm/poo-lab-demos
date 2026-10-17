package lab7.s09_propagation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Slide 9 — propagare și stack trace.
 * Excepția urcă main -> loadReport -> readFile până o prinde cineva.
 */
public class Demo07Propagation {

    static void readFile(String path) throws IOException {
        System.out.println("  readFile: deschid " + path);
        new FileInputStream(path).close();          // aruncă FileNotFoundException
        System.out.println("  readFile: nu ajungem aici");
    }

    static void loadReport(String path) throws IOException {
        System.out.println(" loadReport: start");
        readFile(path);                              // nu tratează -> propagă
        System.out.println(" loadReport: nu ajungem aici");
    }

    static void loadReportWrapped(String path) {
        try {
            readFile(path);
        } catch (IOException e) {
            // ambalare: semnătura rămâne curată, cauza originală NU se pierde
            throw new UncheckedIOException("nu pot încărca raportul", e);
        }
    }

    public static void main(String[] args) {
        System.out.println("main: start");
        try {
            loadReport("raport-lipsa.txt");
        } catch (IOException e) {
            System.out.println("main: prins " + e.getClass().getSimpleName() + "\n");
            System.out.println("--- printStackTrace() — citim de sus în jos:");
            e.printStackTrace(System.out);

            System.out.println("\n--- doar cadrele noastre, prin getStackTrace():");
            for (StackTraceElement el : e.getStackTrace()) {
                if (el.getClassName().startsWith("org.poo")) {
                    System.out.println("  " + el.getMethodName() + " (linia " + el.getLineNumber() + ")");
                }
            }
        }

        System.out.println("\n--- excepție ambalată: apare secțiunea \"Caused by\"");
        try {
            loadReportWrapped("raport-lipsa.txt");
        } catch (UncheckedIOException e) {
            e.printStackTrace(System.out);
        }

        // DEMO: decomentează și lasă excepția să iasă din main -> JVM afișează stack trace-ul pe stderr
        // și programul se termină cu cod de ieșire 1.
        // loadReportWrapped("raport-lipsa.txt");
    }
}
