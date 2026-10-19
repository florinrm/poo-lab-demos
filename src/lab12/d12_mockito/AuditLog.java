package lab12.d12_mockito;

/** Dependență externă: în producție ar scrie într-o bază de date. */
public interface AuditLog {
    void record(String event);
}
