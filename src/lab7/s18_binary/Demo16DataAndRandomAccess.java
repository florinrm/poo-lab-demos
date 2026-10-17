package lab7.s18_binary;

import lab7.DemoFiles;

import java.io.*;

/**
 * Slide 18 [Optional] — DataOutputStream/DataInputStream și RandomAccessFile.
 */
public class Demo16DataAndRandomAccess {

    // fiecare înregistrare: id (int, 4 octeți) + temperatura (double, 8 octeți)
    static final int RECORD_SIZE = Integer.BYTES + Double.BYTES;

    public static void main(String[] args) throws IOException {
        File bin = DemoFiles.file("citiri.bin");

        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(bin)))) {
            for (int id = 0; id < 5; id++) {
                out.writeInt(id);
                out.writeDouble(20 + id * 0.5);
            }
        }
        System.out.println("5 înregistrări x " + RECORD_SIZE + " octeți = " + bin.length() + " octeți");

        System.out.println("\nDataInputStream — citire secvențială în aceeași ordine:");
        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(new FileInputStream(bin)))) {
            while (true) {
                int id = in.readInt();          // aici EOFException e semnalul normal de final
                double t = in.readDouble();
                System.out.println("  #" + id + " " + t);
            }
        } catch (EOFException e) {
            System.out.println("  (EOFException: readInt() nu are o valoare specială pentru final)");
        }

        System.out.println("\nRandomAccessFile — sar direct la înregistrarea 3 și o modific:");
        try (RandomAccessFile raf = new RandomAccessFile(bin, "rw")) {
            raf.seek(3L * RECORD_SIZE);
            System.out.println("  pointer=" + raf.getFilePointer() + " -> #" + raf.readInt() + " " + raf.readDouble());
            raf.seek(3L * RECORD_SIZE + Integer.BYTES);
            raf.writeDouble(-7.0);
            raf.seek(3L * RECORD_SIZE);
            System.out.println("  după scriere -> #" + raf.readInt() + " " + raf.readDouble());
            System.out.println("  length=" + raf.length());
        }

        // Atenție: writeUTF scrie 2 octeți de lungime + șirul, deci are lungime VARIABILĂ.
        // Cu writeUTF în înregistrare, RECORD_SIZE fix nu mai e adevărat fără padding.
    }
}
