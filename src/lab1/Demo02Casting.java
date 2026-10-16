package lab1;

/**
 * Demo 2 - Conversii intre tipuri (casting).
 * Idee: widening e automat, narrowing cere (tip) si poate pierde date.
 */
public class Demo02Casting {
    public static void main(String[] args) {
        // Implicit (widening): mic -> mare, fara pierdere
        byte b = 10;
        int i = b;
        double d = i;
        System.out.println("byte -> int -> double: " + d);

        char c = 'A';
        int code = c;
        System.out.println("char 'A' -> int: " + code);

        // Explicit (narrowing): mare -> mic, trebuie cast
        double pi = 3.99;
        int intPi = (int) pi;
        System.out.println("\n(int) 3.99 = " + intPi + "   <- trunchiere, NU rotunjire");

        int mare = 300;
        byte mic = (byte) mare;
        System.out.println("(byte) 300 = " + mic + "   <- 300 - 256");

        // Literalii: 35.4 este double, deci linia de mai jos NU compileaza
        // float f = 35.4;          // decomentati ca sa aratati eroarea
        float f1 = 35.4f;
        float f2 = (float) 35.4;
        System.out.println("\nfloat: " + f1 + " / " + f2);

        // CAPCANA pentru Task 2 (Leibniz): impartirea intre int-uri
        System.out.println("\n1 / 3    = " + (1 / 3));
        System.out.println("1 / 3.f  = " + (1 / 3.f));
        System.out.println("1 / 3.0  = " + (1 / 3.0));

        // float vs double - precizie
        float fp = 0.1f + 0.2f;
        double dp = 0.1 + 0.2;
        System.out.println("\n0.1 + 0.2 (float)  = " + fp);
        System.out.println("0.1 + 0.2 (double) = " + dp);
    }
}
