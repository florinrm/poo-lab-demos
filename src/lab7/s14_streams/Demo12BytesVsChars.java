package lab7.s14_streams;

import lab7.DemoFiles;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Slide 14 — fluxuri de octeți vs. fluxuri de caractere.
 * Același fișier, citit în trei feluri.
 */
public class Demo12BytesVsChars {

    public static void main(String[] args) throws IOException {
        File f = DemoFiles.file("diacritice.txt");
        String text = "Știință în Iași";

        try (Writer w = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8)) {
            w.write(text);
        }
        System.out.println("text:            " + text);
        System.out.println("caractere:       " + text.length());
        System.out.println("octeți pe disc:  " + f.length() + "   (fiecare diacritică ocupă 2 octeți în UTF-8)\n");

        // 1) byte stream + (char) — fiecare octet devine un "caracter"
        StringBuilder sb = new StringBuilder();
        try (InputStream in = new FileInputStream(f)) {
            int b;
            while ((b = in.read()) != -1) sb.append((char) b);
        }
        System.out.println("FileInputStream + (char):    " + sb);

        // 2) character stream cu charset greșit
        try (Reader r = new InputStreamReader(new FileInputStream(f), StandardCharsets.ISO_8859_1)) {
            System.out.println("Reader cu ISO-8859-1:        " + readAll(r));
        }

        // 3) character stream cu charset corect
        try (Reader r = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8)) {
            System.out.println("Reader cu UTF-8:             " + readAll(r));
        }

        // Din Java 11, FileReader primește și el un Charset:
        try (Reader r = new FileReader(f, StandardCharsets.UTF_8)) {
            System.out.println("FileReader(f, UTF_8):        " + readAll(r));
        }
    }

    static String readAll(Reader r) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = r.read()) != -1) sb.append((char) c);
        return sb.toString();
    }
}
