package lab1.demo;

/**
 * Demo 4 - Structuri de control.
 * Focus pe ce e NOU fata de C: ternar, switch expression, foreach.
 */
public class Demo04Control {
    public static void main(String[] args) {
        int age = 20;

        // if / else if / else
        if (age >= 60) {
            System.out.println("Senior");
        } else if (age >= 18) {
            System.out.println("Adult");
        } else {
            System.out.println("Minor");
        }

        // Acelasi lucru cu ternar (nerecomandat imbricat)
        System.out.println(age >= 18 ? "Adult" : "Minor");

        // switch clasic - atentie la break
        int day = 3;
        switch (day) {
            case 1:
                System.out.println("Luni");
                break;
            case 3:
                System.out.println("Miercuri");
                // fara break -> fall-through! (decomentati break ca sa comparati)
                // break;
            case 4:
                System.out.println("Joi  <- afisat din cauza fall-through");
                break;
            default:
                System.out.println("Alta zi");
        }

        // switch expression (Java 14+): fara break, intoarce valoare
        int value = 5;
        char size = switch (value) {
            case 1, 2, 3 -> 'S';
            case 4 -> 'M';
            case 5, 6 -> 'L';
            default -> 'U';
        };
        System.out.println("\nsize = " + size);

        // for cu doua variabile
        System.out.println("\nfor cu doua variabile:");
        for (int x = 0, y = 10; x < y; x++, y--) {
            System.out.print("(" + x + ", " + y + ") ");
        }
        System.out.println();

        // do-while: ruleaza cel putin o data
        int count = 10;
        do {
            System.out.println("\ndo-while a rulat o data, count = " + count);
            count--;
        } while (count > 10);

        // foreach
        int[] numbers = {1, 2, 3, 4};
        System.out.print("\nforeach: ");
        for (int n : numbers) {
            System.out.print(n + " ");
        }
        System.out.println();

        // continue - doar impare
        System.out.print("continue (impare): ");
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                continue;
            }
            System.out.print(i + " ");
        }
        System.out.println();

        // break opreste doar bucla cea mai apropiata
        System.out.println("\nbreak in bucle imbricate:");
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 10; i++) {
                if (i == 3) {
                    break;
                }
                System.out.print("j=" + j + ",i=" + i + "  ");
            }
            System.out.println();
        }
    }
}
