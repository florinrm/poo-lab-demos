package lab2.s11_params;

/**
 * Slide 11: Java este pass-by-value. Pentru obiecte, valoarea copiată este referința.
 */
class Student {
    String name;
    int averageGrade;

    Student(String name, int averageGrade) {
        this.name = name;
        this.averageGrade = averageGrade;
    }

    @Override
    public String toString() {
        return name + " (" + averageGrade + ")";
    }
}

public class ParameterPassingDemo {

    static void changePrimitive(int v) {
        v = 100;                               // modifică doar copia
    }

    static void changeReference(Student st) {
        st = new Student("Bob", 10);           // copia referinței arată acum altundeva
    }

    static void changeObject(Student st) {
        st.averageGrade = 10;                  // modifică obiectul comun din heap
    }

    static void swap(Student a, Student b) {
        Student tmp = a;
        a = b;
        b = tmp;                               // schimbă doar copiile locale
    }

    public static void main(String[] args) {
        int n = 5;
        changePrimitive(n);
        System.out.println("primitivă:       " + n);

        Student s = new Student("Alice", 5);
        changeReference(s);
        System.out.println("changeReference: " + s);

        changeObject(s);
        System.out.println("changeObject:    " + s);

        Student x = new Student("X", 1);
        Student y = new Student("Y", 2);
        swap(x, y);
        System.out.println("după swap:       x = " + x + ", y = " + y + "  (swap nu merge în Java!)");
    }
}
