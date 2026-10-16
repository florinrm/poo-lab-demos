package lab2.demo.s09_encapsulation;

/**
 * Slide 9: încapsulare — câmp privat + getter/setter cu validare.
 */
class BankAccount {
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.out.println("  Eroare: soldul nu poate fi negativ!");
        }
    }
}

/** Aceeași clasă, dar cu câmp public — nimeni nu ne oprește. */
class OpenBankAccount {
    public double balance;
}

public class EncapsulationDemo {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount();
        acc.setBalance(1000);
        System.out.println("Sold: " + acc.getBalance());

        acc.setBalance(-500);
        System.out.println("Sold: " + acc.getBalance() + "  (neschimbat)");

        // acc.balance = -500;   // decomentați → eroare de compilare (private)

        OpenBankAccount open = new OpenBankAccount();
        open.balance = -500;     // compilează și „strică” obiectul
        System.out.println("\nCont fără încapsulare: " + open.balance);
    }
}
