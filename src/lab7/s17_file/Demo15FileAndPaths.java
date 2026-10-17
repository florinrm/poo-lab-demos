package lab7.s17_file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Slide 17 — clasa File, căi relative, și varianta modernă java.nio.file.
 */
public class Demo15FileAndPaths {

    public static void main(String[] args) throws IOException {
        System.out.println("user.dir (de aici se rezolvă căile relative): " + System.getProperty("user.dir"));

        File f = new File("demo-files" + File.separator + "fantoma.txt");
        System.out.println("\nnew File(...) creează fișierul? exists() = " + f.exists());
        System.out.println("cale absolută: " + f.getAbsolutePath());

        File dir = new File("demo-files", "sub/folder");
        System.out.println("\nmkdirs(): " + dir.mkdirs() + "  (false dacă exista deja)");
        File created = new File(dir, "nou.txt");
        System.out.println("createNewFile(): " + created.createNewFile() + ", exists() = " + created.exists());
        System.out.println("isFile=" + created.isFile() + ", isDirectory=" + dir.isDirectory()
                + ", length=" + created.length());

        System.out.println("\nconținutul lui demo-files/:");
        File[] list = new File("demo-files").listFiles();
        if (list != null) {
            for (File x : list) System.out.println("  " + (x.isDirectory() ? "[d] " : "    ") + x.getName());
        }

        // [Nice to know] java.nio.file — ce se folosește azi în cod nou
        Path p = Path.of("demo-files", "nio.txt");
        Files.write(p, List.of("linia 1", "linia 2", "ăîșț"), StandardCharsets.UTF_8);
        List<String> lines = Files.readAllLines(p, StandardCharsets.UTF_8);
        System.out.println("\nFiles.readAllLines: " + lines);
        System.out.println("Files.exists: " + Files.exists(p) + ", Files.size: " + Files.size(p) + " octeți");
    }
}
