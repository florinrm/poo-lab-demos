package lab4.d06_default;

/**
 * Slide 9 [Optional]: metode default si "problema diamantului".
 *
 * Reguli Java pentru conflicte de metode default:
 *  1. clasa castiga: o metoda din clasa/superclasa bate orice default din interfete;
 *  2. interfata mai specifica castiga (sub-interfata bate super-interfata);
 *  3. altfel -> ambiguitate: clasa TREBUIE sa suprascrie (si poate alege cu X.super.m()).
 */

// ---- 0. default = poti adauga o metoda fara sa strici clasele existente ----
interface Greeter {
    String name();

    default String greet() { // adaugata "mai tarziu"; Robot nu a trebuit modificat
        return "Salut, sunt " + name();
    }
}

class Robot implements Greeter {
    @Override
    public String name() {
        return "R2";
    }
}

// ---- 3. conflict: doua default-uri cu aceeasi semnatura ----
interface Left {
    default String hello() {
        return "Hello from Left";
    }
}

interface Right {
    default String hello() {
        return "Hello from Right";
    }
}

class Both implements Left, Right {
    // DEMO EROARE: stergeti metoda -> "types Left and Right are incompatible;
    //              class Both inherits unrelated defaults for hello() from types Left and Right"
    @Override
    public String hello() {
        return Left.super.hello() + " + " + Right.super.hello();
    }
}

// ---- diamantul "adevarat": un stramos comun ----
interface Top {
    default String who() {
        return "Top";
    }
}

interface Mid1 extends Top {
    @Override
    default String who() {
        return "Mid1";
    }
}

interface Mid2 extends Top {
    // nu suprascrie who()
}

class Bottom implements Mid1, Mid2 {
    // compileaza fara override: Mid1 e mai specifica decat Top (regula 2)
}

// ---- 1. clasa castiga ----
class Parent {
    public String hello() {
        return "Hello from Parent class";
    }
}

class Child extends Parent implements Left, Right {
    // nimic de suprascris: Parent.hello() bate ambele default-uri (regula 1)
}

public class Demo06DefaultMethods {
    public static void main(String[] args) {
        System.out.println("0. " + new Robot().greet());
        System.out.println("3. " + new Both().hello());
        System.out.println("2. Bottom.who() = " + new Bottom().who());
        System.out.println("1. Child.hello() = " + new Child().hello());
    }
}
