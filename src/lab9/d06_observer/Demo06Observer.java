package lab9.d06_observer;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Slide 6–7 — Observer scris de mână: o agenție de știri și abonații ei.
 */
public class Demo06Observer {

    // Observer: o singură metodă → poate fi și lambda
    interface NewsListener {
        void onNews(String headline);
    }

    // Subject: nu știe ce fac abonații, doar îi anunță
    static class NewsAgency {
        private final List<NewsListener> listeners;

        NewsAgency(List<NewsListener> storage) { this.listeners = storage; }

        void subscribe(NewsListener l) { listeners.add(l); }
        void unsubscribe(NewsListener l) { listeners.remove(l); }

        void publish(String headline) {
            System.out.println("-- publish: " + headline);
            for (NewsListener l : listeners) {
                l.onNews(headline);
            }
        }
    }

    // Observatori concreți
    static class EmailSubscriber implements NewsListener {
        private final String email;
        EmailSubscriber(String email) { this.email = email; }
        public void onNews(String headline) { System.out.println("   email către " + email + ": " + headline); }
    }

    static class Archive implements NewsListener {
        final List<String> saved = new ArrayList<>();
        public void onNews(String headline) { saved.add(headline); }
    }

    // Un abonat care vrea doar o știre, apoi se dezabonează singur
    static class OneShot implements NewsListener {
        private final NewsAgency agency;
        OneShot(NewsAgency agency) { this.agency = agency; }
        public void onNews(String headline) {
            System.out.println("   one-shot a primit: " + headline + " → se dezabonează");
            agency.unsubscribe(this);
        }
    }

    public static void main(String[] args) {
        NewsAgency agency = new NewsAgency(new ArrayList<>());
        EmailSubscriber ana = new EmailSubscriber("ana@stud.acs.pub.ro");
        Archive archive = new Archive();

        agency.subscribe(ana);
        agency.subscribe(archive);
        agency.subscribe(h -> System.out.println("   push pe telefon: " + h.toUpperCase()));   // observer ca lambda

        agency.publish("Sesiunea începe pe 19 ianuarie");

        agency.unsubscribe(ana);
        agency.publish("Rezultatele la colocviu au apărut");
        System.out.println("arhiva: " + archive.saved);

        // Capcană: un observer care se dezabonează în timpul notificării
        NewsAgency fragile = new NewsAgency(new ArrayList<>());
        fragile.subscribe(new OneShot(fragile));
        fragile.subscribe(h -> System.out.println("   al doilea abonat: " + h));
        fragile.subscribe(h -> System.out.println("   al treilea abonat: " + h));
        try {
            fragile.publish("Știre 1");
        } catch (ConcurrentModificationException e) {
            System.out.println("   ArrayList → ConcurrentModificationException (al treilea abonat n-a primit nimic)");
        }

        // Fix: o listă care se poate modifica în timpul parcurgerii (sau iterăm pe o copie)
        NewsAgency robust = new NewsAgency(new CopyOnWriteArrayList<>());
        robust.subscribe(new OneShot(robust));
        robust.subscribe(h -> System.out.println("   al doilea abonat: " + h));
        robust.subscribe(h -> System.out.println("   al treilea abonat: " + h));
        robust.publish("Știre 1");
        robust.publish("Știre 2");

        // Capcană 2: o lambda nu poate fi dezabonată dacă nu i-ai păstrat referința
        NewsAgency leaky = new NewsAgency(new ArrayList<>());
        leaky.subscribe(h -> System.out.println("   lambda: " + h));
        leaky.unsubscribe(h -> System.out.println("   lambda: " + h));   // alt obiect! nu șterge nimic
        leaky.publish("Încă sunt abonat");

        // DEMO EROARE: decomentează → incompatible parameter types in lambda expression (onNews primește un singur String)
        // agency.subscribe((headline, source) -> System.out.println(headline));
    }
}
