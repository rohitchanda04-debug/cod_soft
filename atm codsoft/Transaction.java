import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    public enum Type {
        DEPOSIT, WITHDRAWAL, BALANCE_INQUIRY
    }

    private final Type type;
    private final double amount;
    private final double balanceAfter;
    private final LocalDateTime timestamp;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MMM-yyyy  HH:mm:ss");

    public Transaction(Type type, double amount, double balanceAfter) {
        this.type        = type;
        this.amount      = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp   = LocalDateTime.now();
    }

    @Override
    public String toString() {
        String amtStr = (type == Type.BALANCE_INQUIRY)
                ? "          N/A"
                : String.format("$%12.2f", amount);

        return String.format("  %-18s | Amount: %s | Balance After: $%12.2f | %s",
                type, amtStr, balanceAfter, timestamp.format(FORMATTER));
    }
}
