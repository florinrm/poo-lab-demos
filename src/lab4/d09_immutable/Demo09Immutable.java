package lab4.d09_immutable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Slide 13-14: obiecte imutabile.
 *
 *  1. Address (ca pe pagina, cu numele reparat): imutabila, dar FARA equals/hashCode
 *     -> doua adrese identice nu sunt "egale" si nu merg ca chei in HashSet.
 *  2. LeakyTeam: campuri private final, fara setteri... si totusi se poate modifica.
 *  3. Team: copie defensiva la intrare (constructor) si la iesire (getter).
 *  4. "Modificarea" unui imutabil = obiect nou (withCity, String.toUpperCase).
 */
final class Address {
    private final String street;
    private final String city;
    private final int zipCode;

    Address(String street, String city, int zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    /** "setter" imutabil: intoarce un obiect NOU. */
    public Address withCity(String newCity) {
        return new Address(street, newCity, zipCode);
    }

    @Override
    public String toString() {
        return street + ", " + city + " (" + zipCode + ")";
    }

    // DEMO: decomentati equals + hashCode si rulati din nou sectiunea 1
    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     if (!(o instanceof Address a)) return false;
    //     return zipCode == a.zipCode && street.equals(a.street) && city.equals(a.city);
    // }
    //
    // @Override
    // public int hashCode() {
    //     return Objects.hash(street, city, zipCode);
    // }
}

/** Arata imutabil. Nu e. */
final class LeakyTeam {
    private final String name;
    private final List<String> members;

    LeakyTeam(String name, List<String> members) {
        this.name = name;
        this.members = members;          // pastreaza referinta primita
    }

    public List<String> getMembers() {
        return members;                  // da afara referinta interna
    }

    @Override
    public String toString() {
        return name + members;
    }
}

final class Team {
    private final String name;
    private final List<String> members;

    Team(String name, List<String> members) {
        this.name = name;
        this.members = List.copyOf(members);   // copie defensiva la INTRARE (si nemodificabila)
    }

    public List<String> getMembers() {
        return members;                        // List.copyOf e deja read-only
    }

    @Override
    public String toString() {
        return name + members;
    }
}

public class Demo09Immutable {
    public static void main(String[] args) {
        System.out.println("== 1. Imutabil != comparabil ==");
        Address a1 = new Address("Str. Florilor", "Bucuresti", 12345);
        Address a2 = new Address("Str. Florilor", "Bucuresti", 12345);
        System.out.println("a1.equals(a2)        = " + a1.equals(a2));
        Set<Address> visited = new HashSet<>();
        visited.add(a1);
        System.out.println("visited.contains(a2) = " + visited.contains(a2));

        System.out.println("\n== 2. LeakyTeam: final + fara setteri, dar... ==");
        List<String> names = new ArrayList<>(List.of("Ana", "Mihai"));
        LeakyTeam leaky = new LeakyTeam("Echipa A", names);
        names.add("Intrus");                       // pe usa din fata (lista primita)
        leaky.getMembers().remove("Ana");          // pe usa din spate (getter)
        System.out.println("leaky = " + leaky);

        System.out.println("\n== 3. Team: copii defensive ==");
        List<String> names2 = new ArrayList<>(List.of("Ana", "Mihai"));
        Team team = new Team("Echipa B", names2);
        names2.add("Intrus");
        try {
            team.getMembers().remove("Ana");
        } catch (UnsupportedOperationException e) {
            System.out.println("remove() prin getter -> UnsupportedOperationException");
        }
        System.out.println("team = " + team);

        System.out.println("\n== 4. Modificare = obiect nou ==");
        Address a3 = a1.withCity("Cluj");
        System.out.println("a1 = " + a1);
        System.out.println("a3 = " + a3);
        String s = "poo";
        s.toUpperCase();                            // rezultatul e aruncat!
        System.out.println("s dupa s.toUpperCase(): " + s);
        s = s.toUpperCase();
        System.out.println("s dupa s = s.toUpperCase(): " + s);
    }
}
