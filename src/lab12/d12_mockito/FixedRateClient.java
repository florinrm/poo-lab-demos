package lab12.d12_mockito;

import java.util.Map;

/**
 * O implementare reală, în memorie. Folosită în testul de integrare (PriceConverterIntegrationTest),
 * unde PriceConverter colaborează cu o componentă adevărată, nu cu un mock.
 */
public class FixedRateClient implements ExchangeRateClient {

    private final Map<String, Double> table;

    public FixedRateClient(Map<String, Double> table) {
        this.table = table;
    }

    @Override
    public double rate(String from, String to) {
        Double r = table.get(from + "->" + to);
        if (r == null) {
            throw new IllegalArgumentException("unknown pair " + from + "->" + to);
        }
        return r;
    }
}
