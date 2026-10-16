package lab5.d11_comparare;

// Slides 14–15 — == vs. equals, String Pool, quiz-ul de pe slide, compareTo.

public class Demo11Comparing {
    public static void main(String[] args) {
        String a = "Java";
        String b = "Java";
        String c = new String("Java");
        String d = String.valueOf(new char[]{'J', 'a', 'v', 'a'});

        System.out.println("a == b        " + (a == b) + "   (același obiect din pool)");
        System.out.println("a == c        " + (a == c) + "  (new → obiect nou)");
        System.out.println("a == d        " + (a == d) + "  (construit la rulare)");
        System.out.println("a.equals(c)   " + a.equals(c));
        System.out.println("a == c.intern() " + (a == c.intern()));
        System.out.println("FOO vs foo: equals=" + "FOO".equals("foo") + ", equalsIgnoreCase=" + "FOO".equalsIgnoreCase("foo"));

        System.out.println("\n--- quiz (slide 15) ---");
        String s1 = "POO";
        String s2 = "P" + "OO";                   // constantă → concatenată la compilare
        String s3 = new String("POO");
        String s4 = "  POO".trim();               // trim a avut ce tăia → obiect nou
        String s5 = "POO".trim();                 // nimic de tăiat → același obiect
        System.out.println("s1 == s2  " + (s1 == s2));
        System.out.println("s1 == s3  " + (s1 == s3));
        System.out.println("s1 == s4  " + (s1 == s4));
        System.out.println("s1 == s5  " + (s1 == s5));
        String part = "OO";
        System.out.println("s1 == \"P\" + part  " + (s1 == "P" + part) + "  (part nu e constantă)");

        System.out.println("\n--- compareTo: doar semnul contează ---");
        System.out.println("abc vs def  " + "abc".compareTo("def"));
        System.out.println("abc vs abc  " + "abc".compareTo("abc"));
        System.out.println("abc vs 123  " + "abc".compareTo("123"));
        System.out.println("Zebra vs apple  " + "Zebra".compareTo("apple") + "  (majusculele sunt înaintea minusculelor)");
        System.out.println("\"9\" vs \"10\"  " + "9".compareTo("10") + "  ← lexicografic, nu numeric!");
    }
}
