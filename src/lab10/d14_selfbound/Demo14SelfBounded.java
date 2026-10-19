package lab10.d14_selfbound;

/**
 * Slide 14 — [Optional] Self-bounded generics (CRTP): un tip care se referă la el însuși.
 * Cel mai util caz practic: buildere care se moștenesc fără să piardă înlănțuirea.
 */
public class Demo14SelfBounded {

    // Fără CRTP: metodele din părinte întorc tipul părintelui
    static class PlainBuilder {
        protected final StringBuilder out = new StringBuilder();
        PlainBuilder to(String who) { out.append("to=").append(who).append(' '); return this; }
    }

    static class PlainEmailBuilder extends PlainBuilder {
        PlainEmailBuilder subject(String s) { out.append("subject=").append(s).append(' '); return this; }
        String build() { return "Email[" + out.toString().trim() + "]"; }
    }

    // Cu CRTP: părintele știe tipul copilului (B)
    abstract static class MessageBuilder<B extends MessageBuilder<B>> {
        protected final StringBuilder out = new StringBuilder();

        B to(String who) {
            out.append("to=").append(who).append(' ');
            return self();
        }

        protected abstract B self();
    }

    static class EmailBuilder extends MessageBuilder<EmailBuilder> {
        EmailBuilder subject(String s) { out.append("subject=").append(s).append(' '); return this; }
        protected EmailBuilder self() { return this; }
        String build() { return "Email[" + out.toString().trim() + "]"; }
    }

    static class SmsBuilder extends MessageBuilder<SmsBuilder> {
        SmsBuilder text(String t) { out.append("text=").append(t); return this; }
        protected SmsBuilder self() { return this; }
        String build() { return "SMS[" + out.toString().trim() + "]"; }
    }

    // Exemplul de pe pagină — și limita lui
    interface SelfComparable<T extends SelfComparable<T>> {
        int compareTo(T other);
    }

    static class Car implements SelfComparable<Car> {
        public int compareTo(Car other) { return 0; }
    }

    // Compilează! Self-bound-ul nu garantează că T e chiar clasa curentă.
    static class Bus implements SelfComparable<Car> {
        public int compareTo(Car other) { return 1; }
    }

    enum Day { MON, TUE }
    enum Month { JAN, FEB }

    public static void main(String[] args) {
        EmailBuilder eb = new EmailBuilder();
        String email = eb.to("ana@stud.acs.pub.ro").subject("Lab 10").build();   // to() întoarce EmailBuilder
        String sms = new SmsBuilder().to("0722000000").text("Laboratorul începe la 14").build();
        System.out.println(email);
        System.out.println(sms);

        PlainBuilder plain = new PlainEmailBuilder().to("ion");   // tipul s-a "pierdut" la PlainBuilder
        System.out.println("fără CRTP, după to() avem: " + plain.getClass().getSimpleName() + " văzut ca PlainBuilder");

        System.out.println("Bus compară cu Car: " + new Bus().compareTo(new Car()) + "  ← permis de compilator");

        // Enum<E extends Enum<E>>: un enum se compară doar cu valori din același enum
        System.out.println("MON vs TUE: " + Day.MON.compareTo(Day.TUE));

        // DEMO EROARE: decomentează → cannot find symbol: subject(String) — fără CRTP, to() a întors PlainBuilder
        // new PlainEmailBuilder().to("ion").subject("Lab 10");
    }
}
