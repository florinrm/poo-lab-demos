package lab1;

/**
 * Demo 3 - Afisare in consola.
 * Tip IntelliJ: scrieti "sout" + Enter.
 */
public class Demo03Afisare {
    public static void main(String[] args) {
        // print vs println
        System.out.print("Hello");
        System.out.print(" World");
        System.out.println("!");

        // Concatenare cu +
        String nume = "Ana";
        int varsta = 20;
        System.out.println("Numele este " + nume + " si are " + varsta + " ani.");

        // CAPCANA: evaluare de la stanga la dreapta
        System.out.println("\nSum: " + 1 + 2);      // ?
        System.out.println("Sum: " + (1 + 2));      // ?
        System.out.println(1 + 2 + " = suma");      // ?

        // printf - control asupra formatului
        double pi = Math.PI;
        System.out.printf("%npi = %.2f%n", pi);
        System.out.printf("pi = %.6f%n", pi);
        System.out.printf("|%5d|%-5d|%05d|%n", 42, 42, 42);   // aliniere
    }
}
