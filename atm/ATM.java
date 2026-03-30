/**
 * ATM.java
 * Represents the ATM machine — handles authentication, UI, and transaction logic.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATM {

    // ─── Constants ──────────────────────────────────────────────────────────────
    private static final double MAX_WITHDRAWAL_PER_TX = 10_000.00;
    private static final double MAX_DEPOSIT_PER_TX    = 50_000.00;
    private static final int    MAX_PIN_ATTEMPTS      = 3;
    private static final String SEPARATOR_THICK = "═══════════════════════════════════════════════════════";
    private static final String SEPARATOR_THIN  = "───────────────────────────────────────────────────────";

    // ─── Fields ─────────────────────────────────────────────────────────────────
    private final BankAccount account;
    private final String      pin;
    private final List<Transaction> transactionHistory;
    private final Scanner     scanner;
    private boolean           isAuthenticated;

    // ─── Constructor ────────────────────────────────────────────────────────────
    public ATM(BankAccount account, String pin) {
        this.account            = account;
        this.pin                = pin;
        this.transactionHistory = new ArrayList<>();
        this.scanner            = new Scanner(System.in);
        this.isAuthenticated    = false;
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  ENTRY POINT
    // ════════════════════════════════════════════════════════════════════════════

    /** Starts the ATM session. */
    public void start() {
        printWelcomeBanner();
        if (authenticate()) {
            runMainMenu();
        }
        printGoodbyeMessage();
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  AUTHENTICATION
    // ════════════════════════════════════════════════════════════════════════════

    private boolean authenticate() {
        System.out.println("\n  Please insert your card and enter your PIN.");
        System.out.println(SEPARATOR_THIN);

        for (int attempt = 1; attempt <= MAX_PIN_ATTEMPTS; attempt++) {
            System.out.printf("  PIN (attempt %d/%d): ", attempt, MAX_PIN_ATTEMPTS);
            String enteredPin = scanner.nextLine().trim();

            if (enteredPin.equals(pin)) {
                isAuthenticated = true;
                System.out.println();
                System.out.println("  ✔  Authentication successful.");
                System.out.printf("  Welcome back, %s!%n", account.getAccountHolderName());
                System.out.println(SEPARATOR_THICK);
                return true;
            } else {
                int remaining = MAX_PIN_ATTEMPTS - attempt;
                if (remaining > 0) {
                    System.out.printf("  ✘  Incorrect PIN. %d attempt(s) remaining.%n%n", remaining);
                }
            }
        }

        System.out.println();
        System.out.println("  ✘  Too many incorrect attempts. Your card has been blocked.");
        System.out.println("     Please contact your bank for assistance.");
        System.out.println(SEPARATOR_THICK);
        return false;
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  MAIN MENU
    // ════════════════════════════════════════════════════════════════════════════

    private void runMainMenu() {
        boolean running = true;

        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> handleCheckBalance();
                case "2" -> handleDeposit();
                case "3" -> handleWithdrawal();
                case "4" -> handleTransactionHistory();
                case "5" -> {
                    running = false;
                    System.out.println();
                    System.out.println("  Logging out...");
                }
                default  -> System.out.println("\n  [!] Invalid option. Please enter a number between 1 and 5.\n");
            }
        }
    }

    private void printMainMenu() {
        System.out.println();
        System.out.println(SEPARATOR_THICK);
        System.out.println("                     MAIN MENU");
        System.out.println(SEPARATOR_THICK);
        System.out.println("    1.  Check Balance");
        System.out.println("    2.  Deposit");
        System.out.println("    3.  Withdraw");
        System.out.println("    4.  Transaction History");
        System.out.println("    5.  Exit / Log Out");
        System.out.println(SEPARATOR_THIN);
        System.out.print("  Select an option: ");
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  OPERATIONS
    // ════════════════════════════════════════════════════════════════════════════

    /** Check Balance */
    private void handleCheckBalance() {
        System.out.println();
        System.out.println(SEPARATOR_THICK);
        System.out.println("                   ACCOUNT BALANCE");
        System.out.println(SEPARATOR_THIN);
        System.out.printf("  Account Holder : %s%n", account.getAccountHolderName());
        System.out.printf("  Account Number : %s%n", account.getAccountNumber());
        System.out.printf("  Available Bal. : $%.2f%n", account.getBalance());
        System.out.println(SEPARATOR_THICK);

        transactionHistory.add(new Transaction(
                Transaction.Type.BALANCE_INQUIRY, 0, account.getBalance()));
    }

    /** Deposit */
    private void handleDeposit() {
        System.out.println();
        System.out.println(SEPARATOR_THICK);
        System.out.println("                      DEPOSIT");
        System.out.println(SEPARATOR_THIN);
        System.out.printf("  Maximum deposit per transaction: $%.2f%n", MAX_DEPOSIT_PER_TX);
        System.out.printf("  Current Balance               : $%.2f%n", account.getBalance());
        System.out.println(SEPARATOR_THIN);

        double amount = promptForAmount("  Enter deposit amount: $");
        if (amount < 0) return; // user cancelled

        if (amount > MAX_DEPOSIT_PER_TX) {
            System.out.printf("%n  [ERROR] Cannot deposit more than $%.2f in a single transaction.%n",
                    MAX_DEPOSIT_PER_TX);
            return;
        }

        System.out.println();
        if (account.deposit(amount)) {
            transactionHistory.add(new Transaction(
                    Transaction.Type.DEPOSIT, amount, account.getBalance()));
            System.out.println(SEPARATOR_THICK);
            System.out.println("  ✔  Deposit Successful!");
            System.out.printf("  Deposited Amount  : $%.2f%n", amount);
            System.out.printf("  Updated Balance   : $%.2f%n", account.getBalance());
            System.out.println(SEPARATOR_THICK);
        }
    }

    /** Withdrawal */
    private void handleWithdrawal() {
        System.out.println();
        System.out.println(SEPARATOR_THICK);
        System.out.println("                     WITHDRAWAL");
        System.out.println(SEPARATOR_THIN);
        System.out.printf("  Maximum withdrawal per transaction: $%.2f%n", MAX_WITHDRAWAL_PER_TX);
        System.out.printf("  Current Balance                  : $%.2f%n", account.getBalance());
        System.out.println(SEPARATOR_THIN);

        double amount = promptForAmount("  Enter withdrawal amount: $");
        if (amount < 0) return; // user cancelled

        if (amount > MAX_WITHDRAWAL_PER_TX) {
            System.out.printf("%n  [ERROR] Cannot withdraw more than $%.2f in a single transaction.%n",
                    MAX_WITHDRAWAL_PER_TX);
            return;
        }

        System.out.println();
        if (account.withdraw(amount)) {
            transactionHistory.add(new Transaction(
                    Transaction.Type.WITHDRAWAL, amount, account.getBalance()));
            System.out.println(SEPARATOR_THICK);
            System.out.println("  ✔  Withdrawal Successful! Please collect your cash.");
            System.out.printf("  Withdrawn Amount  : $%.2f%n", amount);
            System.out.printf("  Remaining Balance : $%.2f%n", account.getBalance());
            System.out.println(SEPARATOR_THICK);
        }
    }

    /** Transaction History */
    private void handleTransactionHistory() {
        System.out.println();
        System.out.println(SEPARATOR_THICK);
        System.out.println("                 TRANSACTION HISTORY");
        System.out.println(SEPARATOR_THIN);

        if (transactionHistory.isEmpty()) {
            System.out.println("  No transactions recorded in this session.");
        } else {
            System.out.printf("  Total transactions this session: %d%n%n", transactionHistory.size());
            for (int i = 0; i < transactionHistory.size(); i++) {
                System.out.printf("  #%02d  %s%n", (i + 1), transactionHistory.get(i));
            }
        }
        System.out.println(SEPARATOR_THICK);
    }
    private double promptForAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("c") || input.equalsIgnoreCase("cancel")) {
                System.out.println("\n  Transaction cancelled.");
                return -1;
            }

            try {
                double amount = Double.parseDouble(input);
                if (amount <= 0) {
                    System.out.println("  [ERROR] Amount must be greater than zero. (Enter 'c' to cancel)");
                } else if (amount != Math.floor(amount * 100) / 100) {
                    System.out.println("  [ERROR] Amount cannot have more than 2 decimal places.");
                } else {
                    return amount;
                }
            } catch (NumberFormatException e) {
                System.out.println("  [ERROR] Invalid input. Please enter a numeric value. (Enter 'c' to cancel)");
            }
        }
    }
    private void printWelcomeBanner() {
        System.out.println();
        System.out.println(SEPARATOR_THICK);
        System.out.println(" BANK");
        System.out.println();
        System.out.println("          Automated Teller Machine  v1.0");
        System.out.println("          Secure • Fast • Reliable");
        System.out.println(SEPARATOR_THICK);
    }

    private void printGoodbyeMessage() {
        System.out.println();
        System.out.println(SEPARATOR_THICK);
        System.out.println("  Thank you for using our ATM service.");
        System.out.println("  Please take your card. Have a great day!");
        System.out.println(SEPARATOR_THICK);
        System.out.println();
    }
}
