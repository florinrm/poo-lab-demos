package lab7;

import java.io.File;

/** Toate demo-urile scriu în folderul demo-files/ din rădăcina proiectului. */
public final class DemoFiles {
    private DemoFiles() {}

    public static File file(String name) {
        File dir = new File("demo-files");
        dir.mkdirs();
        return new File(dir, name);
    }
}
