package lab1.demo;

/**
 * Demo 6 - Argumente din linia de comanda si System Properties.
 *
 * In IntelliJ: Run -> Edit Configurations...
 *   Program arguments: Hello World
 *   VM options:        -Dname=Ion -Dsurname=Popescu
 * (VM options se activeaza din "Modify options" daca nu apar.)
 */
public class Demo06ArgsSiProprietati {
    public static void main(String[] args) {
        System.out.println("Numar argumente: " + args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.println("args[" + i + "] = " + args[i]);
        }

        String surname = System.getProperty("surname");
        String name = System.getProperty("name");
        System.out.println("\nSystem properties: " + surname + " " + name);

        // Cateva proprietati care exista mereu
        System.out.println("\njava.version = " + System.getProperty("java.version"));
        System.out.println("os.name      = " + System.getProperty("os.name"));
        System.out.println("user.dir     = " + System.getProperty("user.dir"));
    }
}
