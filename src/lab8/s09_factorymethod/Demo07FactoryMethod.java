package lab8.s09_factorymethod;

/**
 * Slide 9 — Factory Method.
 * Creatorul abstract are logica comună (notify) și lasă subclasele să decidă
 * ce canal concret se creează. Adăugăm Push fără să atingem codul existent.
 */
public class Demo07FactoryMethod {

    // Produsul
    interface Channel {
        void deliver(String msg);
    }

    // Produse concrete
    static class EmailChannel implements Channel {
        public void deliver(String msg) { System.out.println("   email: " + msg); }
    }

    static class SmsChannel implements Channel {
        public void deliver(String msg) {
            System.out.println("   sms: " + (msg.length() > 20 ? msg.substring(0, 20) + "..." : msg));
        }
    }

    // Creatorul abstract: logica comună + factory method
    static abstract class Notifier {
        protected abstract Channel createChannel();          // factory method

        public void notifyUser(String msg) {
            Channel ch = createChannel();                    // nu știe ce tip concret primește
            System.out.println("[" + getClass().getSimpleName() + "] trimit prin "
                    + ch.getClass().getSimpleName());
            ch.deliver(msg);
        }
    }

    // Creatori concreți
    static class EmailNotifier extends Notifier {
        protected Channel createChannel() { return new EmailChannel(); }
    }

    static class SmsNotifier extends Notifier {
        protected Channel createChannel() { return new SmsChannel(); }
    }

    // ---- Extindere: produs nou + creator nou. Nimic de mai sus nu se schimbă (OCP).
    static class PushChannel implements Channel {
        public void deliver(String msg) { System.out.println("   push: " + msg); }
    }

    static class PushNotifier extends Notifier {
        protected Channel createChannel() { return new PushChannel(); }
    }

    public static void main(String[] args) {
        Notifier[] notifiers = { new EmailNotifier(), new SmsNotifier(), new PushNotifier() };
        for (Notifier n : notifiers) {
            n.notifyUser("Comanda ta a fost expediată azi");
        }

        // Observație: cineva tot scrie „new EmailNotifier()" — alegerea creatorului
        // concret s-a mutat aici, la marginea programului, nu a dispărut.

        // DEMO EROARE: decomentează -> "Notifier is abstract; cannot be instantiated"
        // Notifier n = new Notifier();
    }
}
