package lab7.s08_custom;

/**
 * Slide 8 — excepții proprii.
 * Ierarhie mică, mesaj util, câmpuri cu context, cauză păstrată.
 */
public class Demo06CustomExceptions {

    static class SensorException extends Exception {
        SensorException(String msg) { super(msg); }
        SensorException(String msg, Throwable cause) { super(msg, cause); }
    }

    static class SensorOfflineException extends SensorException {
        SensorOfflineException(String id) { super("senzorul " + id + " nu răspunde"); }
    }

    static class ReadingOutOfRangeException extends SensorException {
        private final double value;
        ReadingOutOfRangeException(double value) {
            super("citire imposibilă: " + value + "°C");
            this.value = value;
        }
        double getValue() { return value; }
    }

    static double read(String sensorId, String raw) throws SensorException {
        if (raw == null) throw new SensorOfflineException(sensorId);
        double v;
        try {
            v = Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            // traducem excepția de nivel jos într-una a domeniului nostru, PĂSTRÂND cauza
            throw new SensorException("date corupte de la " + sensorId, e);
        }
        if (v < -90 || v > 60) throw new ReadingOutOfRangeException(v);
        return v;
    }

    public static void main(String[] args) {
        String[][] samples = {{"S1", "21.5"}, {"S2", null}, {"S3", "999"}, {"S4", "2x.1"}};

        for (String[] s : samples) {
            try {
                System.out.println(s[0] + ": " + read(s[0], s[1]) + "°C");
            } catch (SensorOfflineException e) {           // specific
                System.out.println(s[0] + ": OFFLINE — " + e.getMessage());
            } catch (ReadingOutOfRangeException e) {       // specific, cu context
                System.out.println(s[0] + ": aberant (" + e.getValue() + ") — ignor citirea");
            } catch (SensorException e) {                  // părintele, la final
                System.out.println(s[0] + ": " + e.getMessage() + " | cauza: " + e.getCause());
            }
        }
    }
}
