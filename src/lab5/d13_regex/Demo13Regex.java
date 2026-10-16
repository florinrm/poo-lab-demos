package lab5.d13_regex;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Slide 17 — căutare, split, regex.

public class Demo13Regex {
    public static void main(String[] args) {
        String url = "http://foo.bar.com/";
        System.out.println("startsWith http: " + url.startsWith("http:") + ", endsWith /: " + url.endsWith("/"));
        String abc = "abcdefghijklmnopqrstuvwxyz";
        System.out.println("indexOf p=" + abc.indexOf('p') + ", def=" + abc.indexOf("def") + ", Fang=" + abc.indexOf("Fang"));
        System.out.println("contains: " + "There is an emergency in sector 6!".contains("emergency"));

        System.out.println("\n--- split ---");
        System.out.println(Arrays.toString("Now is the time for all good people".split("\\s")));
        System.out.println(Arrays.toString("4231,     Java Programming, 1000.00".split("\\s*,\\s*")));
        System.out.println("split(\".\")   → " + Arrays.toString("a.b.c".split(".")) + "  (. = orice caracter)");
        System.out.println("split(\"\\\\.\") → " + Arrays.toString("a.b.c".split("\\.")));

        System.out.println("\n--- replace vs. replaceAll ---");
        System.out.println("replace(\".\", \"-\")    " + "a.b.c".replace(".", "-"));
        System.out.println("replaceAll(\".\", \"-\") " + "a.b.c".replaceAll(".", "-"));

        System.out.println("\n--- matches / Pattern / Matcher ---");
        System.out.println("42.0f număr Java? " + Pattern.matches("\\d+\\.\\d+f?", "42.0f"));
        System.out.println("email minimal? " + "ana@example.com".matches("[^@]+@[^@]+"));
        Matcher m = Pattern.compile("horse|course").matcher("A horse is a horse, of course of course");
        while (m.find()) {
            System.out.println("Matched: '" + m.group() + "' at position " + m.start());
        }

        System.out.println("\n--- trim vs. strip ---");
        String em = " POO ";            // em space (Unicode, cod > 32)
        System.out.println("trim:  [" + em.trim() + "] length " + em.trim().length());
        System.out.println("strip: [" + em.strip() + "] length " + em.strip().length());
    }
}
