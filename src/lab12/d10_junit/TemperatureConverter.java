package lab12.d10_junit;

/**
 * Slide 10–11 — Clasa testată în TemperatureConverterTest (src/test/java).
 */
public class TemperatureConverter {

    public static final double ABSOLUTE_ZERO = -273.15;

    public double toFahrenheit(double celsius) {
        requirePhysical(celsius);
        return celsius * 9 / 5 + 32;
    }

    public double toKelvin(double celsius) {
        requirePhysical(celsius);
        return celsius - ABSOLUTE_ZERO;
    }

    /** FROZEN sub 0, COLD sub 15, MILD sub 25, altfel HOT. */
    public String describe(double celsius) {
        requirePhysical(celsius);
        if (celsius < 0) {
            return "FROZEN";
        } else if (celsius < 15) {
            return "COLD";
        } else if (celsius < 25) {
            return "MILD";
        }
        return "HOT";
    }

    private void requirePhysical(double celsius) {
        if (celsius < ABSOLUTE_ZERO) {
            throw new IllegalArgumentException("below absolute zero: " + celsius);
        }
    }
}
