package lab1.demo;

/**
 * Demo 5 - Array-uri si matrice.
 * Idee: new aloca, .length vine cu array-ul, indexare de la 0.
 */
public class Demo05Arrays {
    public static void main(String[] args) {
        // Initializare cu valori cunoscute
        String[] cars = {"Volvo", "BMW", "Ford"};
        System.out.println("cars.length = " + cars.length);
        System.out.println("cars[0] = " + cars[0]);

        // Alocare + populare
        int[] a = new int[5];
        System.out.print("\nValori implicite dupa new: ");
        for (int x : a) {
            System.out.print(x + " ");            // 0 0 0 0 0 - nu garbage ca in C
        }
        for (int i = 0; i < a.length; i++) {
            a[i] = (i + 1) * 10;
        }
        System.out.print("\nDupa populare: ");
        for (int x : a) {
            System.out.print(x + " ");
        }
        System.out.println();

        // Matrice
        int[][] m = {
            {1, 2, 3},
            {4, 5, 6}
        };
        System.out.println("\nm[1][2] = " + m[1][2]);
        System.out.println("linii = " + m.length + ", coloane = " + m[0].length);
        for (int[] row : m) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }

        // Matrice "zimtata" - liniile pot avea lungimi diferite (util la Pascal!)
        int[][] jagged = new int[4][];
        for (int i = 0; i < jagged.length; i++) {
            jagged[i] = new int[i + 1];
        }
        System.out.println("\nLungimi linii in matricea zimtata:");
        for (int[] row : jagged) {
            System.out.println(row.length);
        }

        // char array (util la Task 3)
        char[] word = {'J', 'a', 'v', 'a'};
        System.out.println("\nchar array: " + new String(word));
        System.out.println("isLetter('7') = " + Character.isLetter('7'));
        System.out.println("toLowerCase('A') = " + Character.toLowerCase('A'));

        // Ce se intampla cand iesim din array? Decomentati:
        // System.out.println(a[5]);   // ArrayIndexOutOfBoundsException, nu comportament nedefinit ca in C
    }
}
