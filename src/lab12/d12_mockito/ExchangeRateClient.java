package lab12.d12_mockito;

/** Dependență externă: în producție ar face un request HTTP către un serviciu de curs valutar. */
public interface ExchangeRateClient {
    double rate(String from, String to);
}
