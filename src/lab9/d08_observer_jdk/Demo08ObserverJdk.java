package lab9.d08_observer_jdk;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Slide 8 — Observer fără să scriem interfețele: Consumer&lt;T&gt; și PropertyChangeSupport.
 * java.util.Observer / Observable sunt deprecated din Java 9 — nu le folosiți.
 */
public class Demo08ObserverJdk {

    // Varianta 1: listă de Consumer<T> — orice lambda e un observer
    static class Thermometer {
        private final List<Consumer<Double>> listeners = new ArrayList<>();
        private double value;

        void onChange(Consumer<Double> listener) { listeners.add(listener); }

        void set(double v) {
            value = v;
            listeners.forEach(l -> l.accept(v));
        }
    }

    // Varianta 2: PropertyChangeSupport — primești și valoarea veche, și pe cea nouă
    static class Volume {
        private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
        private int level = 5;

        void addListener(PropertyChangeListener l) { pcs.addPropertyChangeListener(l); }
        void removeListener(PropertyChangeListener l) { pcs.removePropertyChangeListener(l); }

        void setLevel(int newLevel) {
            int old = level;
            level = newLevel;
            pcs.firePropertyChange("level", old, newLevel);   // nu anunță dacă old == new
        }
    }

    public static void main(String[] args) {
        Thermometer t = new Thermometer();
        t.onChange(v -> System.out.println("display: " + v + " °C"));
        t.onChange(v -> { if (v > 30) System.out.println("ALERTĂ: caniculă"); });
        t.set(21.5);
        t.set(34.0);

        Volume vol = new Volume();
        PropertyChangeListener logger = (PropertyChangeEvent e) ->
                System.out.println(e.getPropertyName() + ": " + e.getOldValue() + " → " + e.getNewValue());
        vol.addListener(logger);
        vol.setLevel(7);
        vol.setLevel(7);                 // aceeași valoare: niciun eveniment
        vol.setLevel(2);
        vol.removeListener(logger);
        vol.setLevel(10);                // nimeni nu mai ascultă
        System.out.println("(după removeListener nu se mai afișează nimic)");

        // DEMO EROARE: decomentează → compilează, dar cu warning [deprecation]: java.util.Observable e deprecated
        // java.util.Observable old = new java.util.Observable();
    }
}
