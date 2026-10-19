package lab12.d03_serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Slide 3–4 — Serializarea nativă: Serializable, transient, static, serialVersionUID.
 */
public class Demo03Serialization {

    static class Session implements Serializable {
        private static final long serialVersionUID = 1L;
        static int created = 0;                       // static: aparține clasei, nu se serializează

        private final String user;
        private final ArrayList<String> visited = new ArrayList<>();   // ArrayList e Serializable
        private transient String token;               // nu se salvează

        Session(String user, String token) {
            this.user = user;
            this.token = token;
            created++;
            System.out.println("   constructor Session(" + user + ")");
        }

        void visit(String page) { visited.add(page); }

        @Override
        public String toString() { return "Session{" + user + ", visited=" + visited + ", token=" + token + "}"; }
    }

    // Nu implementează Serializable
    static class Connection {
        final String url = "jdbc:postgresql://localhost/poo";
    }

    static class SessionWithConnection implements Serializable {
        private static final long serialVersionUID = 1L;
        final Connection connection = new Connection();
    }

    // Fără serialVersionUID explicit: JVM-ul îl calculează din structura clasei
    static class NoUid implements Serializable {
        int x;
    }

    static byte[] toBytes(Object o) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(o);
        }
        return bytes.toByteArray();
    }

    static Object fromBytes(byte[] data) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return in.readObject();
        }
    }

    public static void main(String[] args) throws Exception {
        Session s = new Session("ana", "secret-token-123");
        s.visit("/lab12");
        s.visit("/teme");
        System.out.println("original:      " + s);

        // În fișier, cu try-with-resources (Lab 7)
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("session.ser"))) {
            out.writeObject(s);
        }
        Session.created = 100;                        // schimbăm câmpul static după serializare
        Session copy;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("session.ser"))) {
            copy = (Session) in.readObject();         // constructorul NU e apelat
        }
        System.out.println("deserializat:  " + copy + "  ← token-ul e null (transient)");
        System.out.println("același obiect? " + (s == copy) + ", created = " + Session.created + " (static, nesalvat)");

        // Câți octeți? Formatul conține și numele clasei, câmpurile, UID-ul
        System.out.println("dimensiune în bytes: " + toBytes(s).length);

        // UID-ul declarat vs. cel calculat automat
        System.out.println("UID Session = " + ObjectStreamClass.lookup(Session.class).getSerialVersionUID()
                + ", UID NoUid (calculat) = " + ObjectStreamClass.lookup(NoUid.class).getSerialVersionUID());

        // Un câmp neserializabil strică tot obiectul
        try {
            toBytes(new SessionWithConnection());
        } catch (NotSerializableException e) {
            System.out.println("câmp neserializabil → NotSerializableException: " + e.getMessage());
        }

        System.out.println("round-trip în memorie: " + fromBytes(toBytes(s)));

        // DEMO EROARE: decomentează → NotSerializableException la runtime: Connection nu implementează Serializable
        // toBytes(new Connection());
    }
}
