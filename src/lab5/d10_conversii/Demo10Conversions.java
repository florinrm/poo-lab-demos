package lab5.d10_conversii;

import java.time.LocalDate;

// Slide 13 — strings from things, things from strings.

class Student {
    String name;
    Student(String name) { this.name = name; }
}

public class Demo10Conversions {
    public static void main(String[] args) {
        System.out.println(String.valueOf(1) + String.valueOf(2.384f) + String.valueOf(false));
        System.out.println("fără toString(): " + new Student("Andrei"));
        System.out.println("cu toString():   " + LocalDate.of(2025, 11, 3));

        Object nothing = null;
        System.out.println("valueOf(null) = " + String.valueOf(nothing));
        try {
            nothing.toString();
        } catch (NullPointerException e) {
            System.out.println("null.toString() → NullPointerException");
        }
        try {
            String.valueOf((char[]) null);        // alt overload: valueOf(char[])
        } catch (NullPointerException e) {
            System.out.println("valueOf((char[]) null) → NullPointerException (overload ales la compilare)");
        }

        int n = Integer.parseInt("42");
        double d = Double.parseDouble("99.9999");
        System.out.println("parse: " + (n + 1) + ", " + d);
        System.out.println("hex A = " + Integer.parseInt("A", 16) + ", octal 10 = " + Integer.parseInt("10", 8));

        System.out.println("parseBoolean(\"TRUE\") = " + Boolean.parseBoolean("TRUE"));
        System.out.println("parseBoolean(\"da\")   = " + Boolean.parseBoolean("da") + "   ← fără excepție!");
        try {
            Integer.parseInt("12a");
        } catch (NumberFormatException e) {
            System.out.println("parseInt(\"12a\") → " + e.getMessage());
        }

        // drumul dus-întors: String → int → String
        String code = "41";
        String next = String.valueOf(Integer.parseInt(code) + 1);
        System.out.println(code + " → " + next);
    }
}
