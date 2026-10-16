package lab2.s07_this;

/**
 * Slide 7: cele trei folosiri ale lui this.
 * Group se pasează pe sine fiecărui Student → legătură în ambele sensuri.
 */
class Group {
    private final String name;
    private int numberStudents;
    private final Student[] students;

    Group(String name) {
        this.name = name;                 // (1) dezambiguizare
        numberStudents = 0;
        students = new Student[10];
    }

    boolean addStudent(String name, int grade) {
        if (numberStudents < students.length) {
            students[numberStudents++] = new Student(this, name, grade);   // (2) pasarea lui this
            return true;
        }
        return false;
    }

    String getName() {
        return name;
    }

    void printAll() {
        for (int i = 0; i < numberStudents; i++) {
            System.out.println("  " + students[i]);
        }
    }
}

class Student {
    private final Group group;
    private final String name;
    private final int averageGrade;

    Student(Group group, String name, int averageGrade) {
        this.group = group;
        this.name = name;
        this.averageGrade = averageGrade;
    }

    Student(Group group, String name) {
        this(group, name, 5);             // (3) apel alt constructor
    }

    @Override
    public String toString() {
        return name + " (" + averageGrade + ") din grupa " + group.getName();
    }
}

public class ThisDemo {
    public static void main(String[] args) {
        Group g = new Group("321CA");
        g.addStudent("Ana", 9);
        g.addStudent("Mihai", 8);
        System.out.println("Grupa " + g.getName() + ":");
        g.printAll();

        Student s = new Student(g, "Ioana");
        System.out.println("\nCu notă implicită: " + s);
    }
}
