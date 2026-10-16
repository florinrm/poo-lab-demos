package lab3;

import java.util.Arrays;

/**
 * Slide 4 — agregare vs. compunere.
 *
 * Agregare: obiectul vine din exterior și e partajat → schimbările se văd peste tot.
 * Compunere: containerul își creează (sau își copiază) obiectele → sunt doar ale lui.
 */
public class Demo02AggregationComposition {

    // ---------- Agregare ----------
    static class Employee {
        String name;
        Employee(String name) { this.name = name; }
        @Override public String toString() { return name; }
    }

    static class Department {
        private final String name;
        private final Employee[] employees;

        Department(String name, Employee[] employees) {
            this.name = name;
            this.employees = employees; // păstrăm referința primită → agregare
        }

        @Override public String toString() {
            return name + " " + Arrays.toString(employees);
        }
    }

    // ---------- Compunere ----------
    static class Room {
        final String name;
        Room(String name) { this.name = name; }
        @Override public String toString() { return name; }
    }

    static class House {
        private final Room[] rooms;

        House(int numberOfRooms) {
            rooms = new Room[numberOfRooms]; // creăm în interior → compunere
            for (int i = 0; i < numberOfRooms; i++) {
                rooms[i] = new Room("Camera " + (i + 1));
            }
        }

        @Override public String toString() { return "Casă " + Arrays.toString(rooms); }
    }

    // ---------- Primit din exterior, dar copiat → compunere ----------
    static class Engine {
        int power;
        Engine(int power) { this.power = power; }
    }

    static class SharedEngineCar {
        final Engine engine;
        SharedEngineCar(Engine engine) { this.engine = engine; }           // agregare
    }

    static class OwnEngineCar {
        final Engine engine;
        OwnEngineCar(Engine engine) { this.engine = new Engine(engine.power); } // compunere
    }

    public static void main(String[] args) {
        System.out.println("== Agregare: angajații sunt partajați ==");
        Employee ana = new Employee("Ana");
        Employee[] team = { ana, new Employee("Mihai") };
        Department it = new Department("IT", team);
        Department proiect = new Department("Proiect X", new Employee[] { ana });

        ana.name = "Ana Popescu"; // modificăm din exterior
        System.out.println(it);
        System.out.println(proiect);
        System.out.println("(schimbarea se vede în ambele departamente)");

        System.out.println();
        System.out.println("== Compunere: casa își creează camerele ==");
        System.out.println(new House(3));

        System.out.println();
        System.out.println("== Același motor primit din exterior ==");
        Engine engine = new Engine(100);
        SharedEngineCar shared = new SharedEngineCar(engine);
        OwnEngineCar own = new OwnEngineCar(engine);

        engine.power = 999; // cineva modifică motorul din exterior
        System.out.println("SharedEngineCar (agregare): " + shared.engine.power);
        System.out.println("OwnEngineCar    (compunere): " + own.engine.power);
    }
}
