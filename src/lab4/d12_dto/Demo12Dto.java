package lab4.d12_dto;

import java.util.List;

/**
 * Slide 18: DTO (Data Transfer Object).
 *
 * User = entitatea interna (cu date sensibile). UserDTO = doar ce trimitem in afara.
 * Varianta de pe pagina cu record pierde constructorul UserDTO(User);
 * il recuperam cu o metoda factory statica from(User).
 */
class User {
    private final String name;
    private final String email;
    private final String cnp;
    private final double balance;

    User(String name, String email, String cnp, double balance) {
        this.name = name;
        this.email = email;
        this.cnp = cnp;
        this.balance = balance;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCnp() { return cnp; }
    public double getBalance() { return balance; }

    @Override
    public String toString() { // ce s-ar "scurge" daca trimitem entitatea direct
        return "User{name=" + name + ", email=" + email + ", cnp=" + cnp + ", balance=" + balance + "}";
    }
}

/** Varianta clasica (ca pe pagina), cu getteri ca sa poata fi folosita. */
class UserDTOClassic {
    private final String name;
    private final String email;

    UserDTOClassic(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
}

/** Varianta record + factory. */
record UserDTO(String name, String email) {
    static UserDTO from(User user) {
        return new UserDTO(user.getName(), user.getEmail());
    }
}

public class Demo12Dto {
    public static void main(String[] args) {
        List<User> users = List.of(
                new User("Ana Pop", "ana@example.com", "2990101123456", 12_500.0),
                new User("Ion Ionescu", "ion@example.com", "1980202654321", 300.0));

        System.out.println("== Ce ar pleca spre frontend fara DTO ==");
        users.forEach(u -> System.out.println("  " + u));

        System.out.println("\n== Cu DTO ==");
        users.forEach(u -> System.out.println("  " + UserDTO.from(u)));

        UserDTOClassic c = new UserDTOClassic(users.get(0));
        System.out.println("\nVarianta clasica: " + c.getName() + " <" + c.getEmail() + ">");
        System.out.println("(record-ul ne da gratis toString/equals; clasa clasica nu)");
    }
}
