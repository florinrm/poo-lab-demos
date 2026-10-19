package lab12.d12_mockito;

/**
 * Slide 12 — Clasa testată în PriceConverterTest. Depinde de două interfețe, primite în constructor.
 */
public class PriceConverter {

    private final ExchangeRateClient rates;
    private final AuditLog audit;

    public PriceConverter(ExchangeRateClient rates, AuditLog audit) {
        this.rates = rates;
        this.audit = audit;
    }

    public double convert(double amount, String from, String to) {
        if (amount < 0) {
            throw new IllegalArgumentException("negative amount");
        }
        if (from.equals(to)) {
            return amount;                                    // nu întreabă serviciul extern
        }
        double rate = rates.rate(from, to);
        if (rate <= 0) {
            throw new IllegalStateException("invalid rate " + rate + " for " + from + "->" + to);
        }
        double result = Math.round(amount * rate * 100) / 100.0;
        audit.record(from + "->" + to + ": " + amount + " = " + result);
        return result;
    }
}
