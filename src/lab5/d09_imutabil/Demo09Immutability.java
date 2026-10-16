package lab5.d09_imutabil;

import java.nio.charset.StandardCharsets;

// Slide 12 — String e imutabil; moduri de a crea un String.

public class Demo09Immutability {
    public static void main(String[] args) {
        String s = "  Java  ";
        s.trim();
        s.toUpperCase();
        System.out.println("[" + s + "]   ← rezultatele s-au pierdut");
        s = s.trim().toUpperCase();
        System.out.println("[" + s + "]   ← atribuit");

        // text block: marginea e dată de linia cea mai puțin indentată, INCLUSIV """ de final
        String a = """
            <p>
              Salut
            </p>
            """;
        String b = """
            <p>
              Salut
            </p>
        """;                                      // """ mai la stânga → toată indentarea crește
        System.out.print(a.replace(' ', '·'));
        System.out.print(b.replace(' ', '·'));

        // din char[] și byte[]
        char[] letters = {'L', 'e', 'm', 'm', 'i', 'n', 'g'};
        System.out.println(new String(letters));

        byte[] bytes = {(byte) 0x63, (byte) 0x61, (byte) 0x73, (byte) 0xC4, (byte) 0x83};  // "casă" în UTF-8
        System.out.println("UTF-8:      " + new String(bytes, StandardCharsets.UTF_8));
        System.out.println("ISO-8859-1: " + new String(bytes, StandardCharsets.ISO_8859_1) + "   ← codificare greșită");

        // DEMO EROARE: varianta de pe pagină aruncă o excepție checked (UnsupportedEncodingException)
        // String abc = new String(bytes, "ISO8859_1");

        String w = "Newton";
        System.out.println("length=" + w.length() + ", isEmpty=" + w.isEmpty() + ", charAt(0)=" + w.charAt(0));
        System.out.println("concat: " + "John ".concat("Smith"));
    }
}
