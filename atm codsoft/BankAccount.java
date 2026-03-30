public class BankAccount {

    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private static final double MAX_BALANCE = 1_000_000.00;
    public BankAccount(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("  [ERROR] Deposit amount must be greater than zero.");
            return false;
        }
        if (balance + amount > MAX_BALANCE) {
            System.out.printf("  [ERROR] Deposit would exceed the maximum allowed balance of $%.2f.%n", MAX_BALANCE);
            return false;
        }
        balance += amount;
        return true;
    }
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("  [ERROR] Withdrawal amount must be greater than zero.");
            return false;
        }
        if (amount > balance) {
            System.out.printf("  [ERROR] Insufficient funds. Available balance: $%.2f%n", balance);
            return false;
        }
        balance -= amount;
        return true;
    }
    @Override
    public String toString() {
        return String.format("Account [%s] | Holder: %-20s | Balance: $%.2f",
                accountNumber, accountHolderName, balance);
    }
}
