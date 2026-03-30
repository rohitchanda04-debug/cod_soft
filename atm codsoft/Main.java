public class Main {

    public static void main(String[] args) {
        BankAccount myAccount = new BankAccount(
                "1234567890",       
                "Rohit chanda1",
                5000.00        
        );
        ATM atm = new ATM(myAccount, "1234");
        atm.start();
    }
}
