package lab12.d13_logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Slide 13 — [Nice to know] java.util.logging: niveluri, filtrare, mesaje leneșe.
 */
public class Demo13Logging {

    private static final Logger LOG = Logger.getLogger(Demo13Logging.class.getName());

    static String expensiveDump() {
        System.out.println("   (expensiveDump apelat)");
        return "1000 de linii de stare internă";
    }

    public static void main(String[] args) {
        // Nivelul implicit e INFO: FINE nu apare
        LOG.severe("eroare critică");
        LOG.warning("ceva suspect");
        LOG.info("aplicația a pornit");
        LOG.fine("detaliu de execuție (nu apare)");

        // Capcana: setăm logger-ul pe FINE... și tot nu apare, pentru că handler-ul consolei e pe INFO
        LOG.setLevel(Level.FINE);
        LOG.fine("tot nu apare: handler-ul din root filtrează la INFO");

        // Avem nevoie și de un handler pe FINE
        ConsoleHandler h = new ConsoleHandler();
        h.setLevel(Level.FINE);
        LOG.addHandler(h);
        LOG.setUseParentHandlers(false);          // altfel mesajele INFO+ ar apărea de două ori
        LOG.fine("acum apare");

        // Mesaje leneșe: cu Supplier, string-ul se construiește doar dacă nivelul e activ
        LOG.setLevel(Level.INFO);
        System.out.println("cu concatenare:");
        LOG.fine("stare: " + expensiveDump());    // expensiveDump rulează degeaba
        System.out.println("cu Supplier:");
        LOG.fine(() -> "stare: " + expensiveDump());   // nu rulează

        // Excepții: se loghează cu tot cu stack trace
        try {
            Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            LOG.log(Level.WARNING, "input invalid de la utilizator", e);
        }

        // DEMO EROARE: decomentează → cannot find symbol: debug(String) — JUL are fine/finer/finest, nu debug (acela e SLF4J/Log4j)
        // LOG.debug("detaliu");
    }
}
