package lab3;

/**
 * Slide 13 — clasa Object: toString() și == vs. equals.
 */
public class Demo11ObjectMethods {

    static class PlainStudent {
        String name;
        int averageGrade;

        PlainStudent(String name, int averageGrade) {
            this.name = name;
            this.averageGrade = averageGrade;
        }
    }

    static class Student {
        private final String name;
        private final int averageGrade;

        Student(String name, int averageGrade) {
            this.name = name;
            this.averageGrade = averageGrade;
        }

        // Generat și cu IntelliJ: click dreapta → Generate… → toString()
        @Override
        public String toString() {
            return "Student{name='" + name + "', averageGrade=" + averageGrade + "}";
        }
    }

    public static void main(String[] args) {
        System.out.println("== toString() implicit ==");
        PlainStudent p = new PlainStudent("Ilie Popescu", 5);
        System.out.println("  println(p)              = " + p);
        System.out.println("  hashCode() în hexa      = " + Integer.toHexString(p.hashCode()));
        System.out.println("  (după @ e hashCode-ul în hexa, nu adresa din memorie)");

        System.out.println();
        System.out.println("== toString() suprascris ==");
        Student s = new Student("Ilie Popescu", 5);
        System.out.println("  " + s);  // println/concatenarea apelează automat toString()

        System.out.println();
        System.out.println("== == vs. equals pentru String ==");
        String lit1 = "abc";
        String lit2 = "abc";
        String built = new String("abc");
        String fromInput = new StringBuilder("ab").append("c").toString();

        System.out.println("  lit1 == lit2         : " + (lit1 == lit2) + "  <- ambele din String pool");
        System.out.println("  lit1 == built        : " + (lit1 == built));
        System.out.println("  lit1 == fromInput    : " + (lit1 == fromInput));
        System.out.println("  lit1.equals(built)   : " + lit1.equals(built));
        System.out.println("  lit1.equals(fromInput): " + lit1.equals(fromInput));
        System.out.println("  (== „merge” uneori din noroc — pentru String folosiți mereu equals)");
    }
}
