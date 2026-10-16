package lab6.d13_hashcode;

import java.util.*;

/**
 * Slide 13 — Contractul equals / hashCode.
 */
public class Demo13HashCode {

    // Doar equals, fără hashCode → contract încălcat
    static class PointNoHash {
        final int x, y;
        PointNoHash(int x, int y) { this.x = x; this.y = y; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PointNoHash p)) return false;
            return x == p.x && y == p.y;
        }
    }

    // equals + hashCode, dar câmpuri modificabile
    static class Point {
        int x, y;
        Point(int x, int y) { this.x = x; this.y = y; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point p = (Point) o;
            return x == p.x && y == p.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() { return "(" + x + "," + y + ")"; }
    }

    public static void main(String[] args) {
        // 1. Fără hashCode: equals spune "egale", HashSet le ține pe amândouă
        PointNoHash a = new PointNoHash(1, 2), b = new PointNoHash(1, 2);
        Set<PointNoHash> s1 = new HashSet<>(List.of(a));
        s1.add(b);
        System.out.println("fără hashCode: a.equals(b)=" + a.equals(b) + ", size=" + s1.size()
                + ", contains(new(1,2))=" + s1.contains(new PointNoHash(1, 2)));
        // ArrayList folosește doar equals, deci acolo merge
        System.out.println("ArrayList.contains = " + new ArrayList<>(List.of(a)).contains(b));

        // 2. Cu hashCode: totul e în regulă
        Set<Point> s2 = new HashSet<>();
        s2.add(new Point(1, 2));
        s2.add(new Point(1, 2));
        System.out.println("cu hashCode: size=" + s2.size() + ", contains=" + s2.contains(new Point(1, 2)));

        // 3. Hash egal NU înseamnă obiecte egale (coliziune)
        System.out.println("\"Aa\".hashCode()=" + "Aa".hashCode() + ", \"BB\".hashCode()=" + "BB".hashCode()
                + ", equals=" + "Aa".equals("BB"));

        // 4. Modificarea unui câmp din hashCode după inserare → elementul "se pierde"
        Point p = new Point(3, 4);
        Set<Point> s3 = new HashSet<>(List.of(p));
        p.x = 99;
        System.out.println("după p.x=99: set=" + s3 + ", contains(p)=" + s3.contains(p)
                + ", remove(p)=" + s3.remove(p) + ", size=" + s3.size());

        // equals(Bad) e overload, nu override — semnătura corectă e equals(Object); @Override prinde greșeala
        // DEMO EROARE: decomentează → method does not override or implement a method from a supertype
        // class Bad { int v; @Override public boolean equals(Bad o) { return v == o.v; } }
    }
}
