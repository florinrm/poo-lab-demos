package lab1;

/**
 * Demo 1 - Tipuri primitive.
 * Idee: marimi fixe, sufixe L / f, underscore in literali, overflow fara eroare.
 */
public class Demo01Primitive {
    public static void main(String[] args) {
        boolean isValid = true;
        char initial = 'L';
        byte hexCode = (byte) 0xF1;
        short age = 23;
        int credit = -1;
        long userId = 169_234L;
        float percentage = 0.42f;
        double money = 99_999;

        System.out.println("boolean: " + isValid);
        System.out.println("char:    " + initial);
        System.out.println("byte:    " + hexCode + "   <- 0xF1 = 241, dar byte merge doar pana la 127!");
        System.out.println("short:   " + age);
        System.out.println("int:     " + credit);
        System.out.println("long:    " + userId);
        System.out.println("float:   " + percentage);
        System.out.println("double:  " + money);

        // Underscore - doar pentru lizibilitate
        int populatie = 1_800_000_000;
        System.out.println("\n1_800_000_000 = " + populatie);

        // Limitele tipurilor
        System.out.println("\nint  max = " + Integer.MAX_VALUE);
        System.out.println("long max = " + Long.MAX_VALUE);

        // OVERFLOW: nu apare nicio eroare, valoarea "da ocolul"
        byte b = 127;
        b++;
        System.out.println("\nbyte 127 + 1 = " + b);

        int big = Integer.MAX_VALUE;
        System.out.println("Integer.MAX_VALUE + 1 = " + (big + 1));

        // char e pe 16 biti -> Unicode, nu doar ASCII ca in C
        char ro = 'ș';
        char ru = 'Ж';
        System.out.println("\nchar Unicode: " + ro + " " + ru);
        System.out.println("char -> int: 'A' = " + (int) 'A');
    }
}
