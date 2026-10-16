package lab3;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Slide 14 — equals: overload vs. override, plus hashCode.
 *
 * Trei versiuni ale aceleiași clase, testate cu List.contains și HashSet.
 */
public class Demo12Equals {

    /** GREȘIT: equals(Car) este overload, nu override. */
    static class OverloadCar {
        final String name;
        final int year;

        OverloadCar(String name, int year) { this.name = name; this.year = year; }

        public boolean equals(OverloadCar c) {
            return year == c.year && Objects.equals(name, c.name);
        }
    }

    /** equals(Object) corect, dar fără hashCode. */
    static class NoHashCar {
        final String name;
        final int year;

        NoHashCar(String name, int year) { this.name = name; this.year = year; }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof NoHashCar c)) return false;
            return year == c.year && Objects.equals(name, c.name);
        }
    }

    /** Corect: equals(Object) + hashCode pe aceleași câmpuri. */
    static class GoodCar {
        final String name;
        final int year;

        GoodCar(String name, int year) { this.name = name; this.year = year; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GoodCar c)) return false;
            return year == c.year && Objects.equals(name, c.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, year);
        }
    }

    public static void main(String[] args) {
        System.out.println("== OverloadCar: equals(OverloadCar) ==");
        OverloadCar o1 = new OverloadCar("Dacia", 2020);
        OverloadCar o2 = new OverloadCar("Dacia", 2020);
        Object o2AsObject = o2;
        System.out.println("  o1.equals(o2)              : " + o1.equals(o2));
        System.out.println("  o1.equals((Object) o2)     : " + o1.equals(o2AsObject));
        System.out.println("  List.of(o1).contains(o2)   : " + List.of(o1).contains(o2));
        System.out.println("  (colecțiile apelează equals(Object) → se folosește cel din Object)");

        System.out.println();
        System.out.println("== NoHashCar: equals corect, fără hashCode ==");
        NoHashCar n1 = new NoHashCar("Dacia", 2020);
        NoHashCar n2 = new NoHashCar("Dacia", 2020);
        Set<NoHashCar> noHashSet = new HashSet<>();
        noHashSet.add(n1);
        noHashSet.add(n2);
        System.out.println("  n1.equals(n2)              : " + n1.equals(n2));
        System.out.println("  List.of(n1).contains(n2)   : " + List.of(n1).contains(n2));
        System.out.println("  HashSet size după 2 add-uri: " + noHashSet.size() + "  <- ar trebui 1");
        System.out.println("  HashSet cu n1 contains(n2) : " + new HashSet<>(List.of(n1)).contains(n2)
                + "  <- equals zice true, dar HashSet caută după hashCode");

        System.out.println();
        System.out.println("== GoodCar: equals + hashCode ==");
        GoodCar g1 = new GoodCar("Dacia", 2020);
        GoodCar g2 = new GoodCar("Dacia", 2020);
        Set<GoodCar> goodSet = new HashSet<>();
        goodSet.add(g1);
        goodSet.add(g2);
        System.out.println("  g1.equals(g2)              : " + g1.equals(g2));
        System.out.println("  HashSet size după 2 add-uri: " + goodSet.size());
        System.out.println("  g1 == g2                   : " + (g1 == g2) + "  <- tot obiecte diferite");

        System.out.println();
        System.out.println("== null și alt tip ==");
        System.out.println("  g1.equals(null)            : " + g1.equals(null));
        System.out.println("  g1.equals(\"Dacia\")         : " + g1.equals("Dacia"));
    }
}
