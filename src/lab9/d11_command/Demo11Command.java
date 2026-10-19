package lab9.d11_command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Slide 11–12 — Command: un robot pe o grilă, comandat de un controller.
 * Arată execute + undo, macro-comenzi și execuție întârziată. (Redo e Task 3.)
 */
public class Demo11Command {

    // Receiver: face treaba reală, nu știe nimic despre comenzi
    static class Robot {
        int x = 0, y = 0;
        int dir = 0;                                  // 0=N, 1=E, 2=S, 3=V
        private static final String[] NAMES = {"N", "E", "S", "V"};

        void step(int n) {
            switch (dir) {
                case 0 -> y += n;
                case 1 -> x += n;
                case 2 -> y -= n;
                case 3 -> x -= n;
            }
        }
        void turnRight() { dir = (dir + 1) % 4; }
        void turnLeft() { dir = (dir + 3) % 4; }

        @Override
        public String toString() { return "(" + x + "," + y + ") spre " + NAMES[dir]; }
    }

    // Command
    interface Command {
        void execute();
        void undo();
    }

    // Comenzi concrete: țin receiver-ul și argumentele
    static class Move implements Command {
        private final Robot r;
        private final int steps;
        Move(Robot r, int steps) { this.r = r; this.steps = steps; }
        public void execute() { r.step(steps); }
        public void undo() { r.step(-steps); }
        @Override public String toString() { return "Move(" + steps + ")"; }
    }

    static class TurnRight implements Command {
        private final Robot r;
        TurnRight(Robot r) { this.r = r; }
        public void execute() { r.turnRight(); }
        public void undo() { r.turnLeft(); }
        @Override public String toString() { return "TurnRight"; }
    }

    // O comandă compusă din alte comenzi (Composite + Command)
    static class Macro implements Command {
        private final List<Command> steps;
        Macro(List<Command> steps) { this.steps = steps; }
        public void execute() { steps.forEach(Command::execute); }
        public void undo() {
            for (int i = steps.size() - 1; i >= 0; i--) steps.get(i).undo();   // în ordine inversă!
        }
        @Override public String toString() { return "Macro" + steps; }
    }

    // Invoker: execută și ține istoricul; nu știe ce face fiecare comandă
    static class Controller {
        private final Deque<Command> history = new ArrayDeque<>();

        void run(Command c) {
            c.execute();
            history.push(c);
        }

        void undoLast() {
            if (history.isEmpty()) {
                System.out.println("   nimic de anulat");
                return;
            }
            Command c = history.pop();
            c.undo();
            System.out.println("   undo " + c);
        }
    }

    public static void main(String[] args) {
        Robot robot = new Robot();
        Controller ctl = new Controller();

        ctl.run(new Move(robot, 3));
        ctl.run(new TurnRight(robot));
        ctl.run(new Move(robot, 2));
        System.out.println("după 3 comenzi:  " + robot);

        ctl.undoLast();
        ctl.undoLast();
        System.out.println("după 2 undo:     " + robot);

        // Macro: "ocolește un obstacol" = o singură intrare în istoric
        Command detour = new Macro(List.of(
                new TurnRight(robot), new Move(robot, 1),
                new TurnRight(robot), new TurnRight(robot), new TurnRight(robot),
                new Move(robot, 2)));
        ctl.run(detour);
        System.out.println("după macro:      " + robot);
        ctl.undoLast();
        System.out.println("după undo macro: " + robot);

        // Execuție întârziată: comenzile sunt obiecte, le putem stoca într-un "program"
        List<Command> program = new ArrayList<>();
        program.add(new Move(robot, 1));
        program.add(new TurnRight(robot));
        program.add(new Move(robot, 4));
        System.out.println("program salvat:  " + program + ", robotul tot la " + robot);
        program.forEach(ctl::run);
        System.out.println("după program:    " + robot);

        ctl.undoLast(); ctl.undoLast(); ctl.undoLast(); ctl.undoLast(); ctl.undoLast();

        // DEMO EROARE: decomentează → NoSuchElementException la runtime: pop() pe o stivă goală (de aceea verificăm isEmpty)
        // new ArrayDeque<Command>().pop();
    }
}
