package lab8.s08_aggregation;

import java.util.ArrayList;
import java.util.List;

/**
 * Slide 8 — Singleton vs agregare (dependența primită în constructor).
 * Aceeași clasă, scrisă în ambele feluri. Diferența apare la testare.
 */
public class Demo06SingletonVsAggregation {

    interface Mailer { void send(String to, String text); }

    /** Varianta Singleton: „trimite" de-adevăratelea (aici doar afișează). */
    static class SmtpMailer implements Mailer {
        private static final SmtpMailer INSTANCE = new SmtpMailer();
        private SmtpMailer() { }
        static SmtpMailer getInstance() { return INSTANCE; }
        public void send(String to, String text) {
            System.out.println("   [SMTP] email REAL către " + to + ": " + text);
        }
    }

    /** Dependență ascunsă: din semnătură nu vezi că trimite emailuri. */
    static class SignupServiceHidden {
        void register(String user) {
            SmtpMailer.getInstance().send(user, "Bun venit!");
        }
    }

    /** Dependență explicită: o primește în constructor. */
    static class SignupService {
        private final Mailer mailer;
        SignupService(Mailer mailer) { this.mailer = mailer; }
        void register(String user) { mailer.send(user, "Bun venit!"); }
    }

    /** Fals pentru teste: doar notează ce s-ar fi trimis. */
    static class RecordingMailer implements Mailer {
        final List<String> sent = new ArrayList<>();
        public void send(String to, String text) { sent.add(to); }
    }

    public static void main(String[] args) {
        System.out.println("„Test” pe varianta cu Singleton:");
        new SignupServiceHidden().register("ana@test.ro");
        System.out.println("   -> testul a trimis un email real; nu am cum să-l opresc fără să modific clasa\n");

        System.out.println("Test pe varianta cu agregare:");
        RecordingMailer fake = new RecordingMailer();
        new SignupService(fake).register("ana@test.ro");
        System.out.println("   s-ar fi trimis către: " + fake.sent);

        System.out.println("\nÎn aplicația reală: o singură instanță, creată într-un singur loc (main/App):");
        Mailer real = SmtpMailer.getInstance();
        SignupService service = new SignupService(real);
        service.register("ion@exemplu.ro");
    }
}
