package lab12.d07_reflection;

/**
 * Clasă separată (nu internă) ca accesul privat să fie blocat cu adevărat:
 * clasele interne sunt "nestmates" cu clasa exterioară și își pot vedea membrii privați.
 */
public class Account {
    private String owner;
    private double balance;
    public static final String BANK = "POO Bank";

    public Account() { this("anonim", 0); }
    public Account(String owner, double balance) { this.owner = owner; this.balance = balance; }

    public void deposit(double amount) { balance += amount; }
    public double getBalance() { return balance; }
    private String secret() { return "PIN 1234"; }

    @Override
    public String toString() { return owner + ": " + balance; }
}
