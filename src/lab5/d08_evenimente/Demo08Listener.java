package lab5.d08_evenimente;

import java.util.ArrayList;
import java.util.List;

// Slide 11 — callback: butonul MEMOREAZĂ listener-ul și îl apelează la click.
// (Pe pagină, JButton-ul simplificat îl apelează imediat în addActionListener.)

interface ClickListener {
    void onClick(String source);
}

class Button {
    private final String label;
    private final List<ClickListener> listeners = new ArrayList<>();

    Button(String label) { this.label = label; }

    void addListener(ClickListener l) {
        listeners.add(l);                         // doar îl păstrează
        System.out.println("listener înregistrat pe " + label);
    }

    void click() {                                // „utilizatorul” apasă
        for (ClickListener l : listeners) l.onClick(label);
    }
}

public class Demo08Listener {
    private int numClicks = 0;                    // câmp → poate fi modificat din clasa anonimă

    void run() {
        Button ok = new Button("OK");
        ok.addListener(new ClickListener() {
            @Override
            public void onClick(String source) {
                numClicks++;
                System.out.println("click pe " + source + ", total " + numClicks);
            }
        });
        ok.addListener(source -> System.out.println("(lambda) log: " + source));

        System.out.println("încă nimeni n-a apăsat...");
        ok.click();
        ok.click();
    }

    public static void main(String[] args) {
        new Demo08Listener().run();
    }
}
