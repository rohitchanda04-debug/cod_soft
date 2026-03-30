public class Main {

    public static void main(String[] args) {

        // ── Create a demo bank account ──────────────────────────────────────────
        BankAccount myAccount = new BankAccount(
                "1234567890",       // account number
                "Alice Johnson",    // account holder name
                5000.00             // initial balance
        );

        // ── Create the ATM linked to that account (PIN = "1234") ───────────────
        ATM atm = new ATM(myAccount, "1234");

        // ── Launch the ATM session ──────────────────────────────────────────────
        atm.start();
    }
}
