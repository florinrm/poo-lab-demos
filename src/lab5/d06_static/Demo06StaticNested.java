package lab5.d06_static;

// Slide 9 — static nested vs. inner.

class Pencil {
    private String brand = "Faber";
    private static int produced = 0;

    enum Hardness { HB, B2, H2 }                  // enum intern = implicit static

    static class Box {                            // nu are nevoie de un Pencil
        private final int size;
        Box(int size) { this.size = size; }
        void describe() {
            System.out.println("cutie de " + size + ", produse până acum: " + produced);
            // DEMO EROARE: brand e câmp de instanță; Box nu are un Pencil
            // System.out.println(brand);
        }
    }

    class Sharpener {                             // inner: are nevoie de un Pencil
        void sharpen() { System.out.println("ascut un creion " + brand); }
    }

    Pencil() { produced++; }
}

public class Demo06StaticNested {
    public static void main(String[] args) {
        Pencil.Box box = new Pencil.Box(12);      // fără instanță Pencil
        box.describe();

        Pencil p = new Pencil();
        Pencil.Sharpener s = p.new Sharpener();   // cu instanță Pencil
        s.sharpen();
        box.describe();

        Pencil.Hardness h = Pencil.Hardness.HB;   // enum intern, folosit fără Pencil
        System.out.println("duritate: " + h + ", toate: " + java.util.Arrays.toString(Pencil.Hardness.values()));

        // DEMO EROARE: Sharpener e inner — cere un Pencil
        // Pencil.Sharpener s2 = new Pencil.Sharpener();
    }
}
