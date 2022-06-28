import java.util.List;

public class BankAccount {
    private String username;
    private String password;
    private Double balance;
    private List<Double> transactions;

    public BankAccount() {}

    public BankAccount(String username,String password, Double balance, List<Double> transactions) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.transactions = transactions;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Double> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Double> transactions) {
        this.transactions = transactions;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static BankAccount withdrawFromAccount(BankAccount account, double amount){
        if(amount >= 0 && (amount <= account.balance)){
            account.setBalance(account.balance - amount);
            account.transactions.add(0-amount);
            System.out.println("Withdrawal successful! Your new balance is: " + account.balance);
        }else{
            System.out.println("Desired amount exceeds available balance");
        }

        return account;
    }

    public static BankAccount depositIntoAccount(BankAccount account, double amount){
        if(amount > 0){
            account.setBalance(amount+account.balance);
            account.transactions.add(amount);
            System.out.println("Deposit Successful! Your new account balance is: $" + account.balance);
        }else{
            System.out.println("Cannot deposit an amount lower than $0");
        }
        return account;
    }

    public static void viewAllTransactions(BankAccount account){
        if(account.transactions.size() == 0){
            System.out.println("There are no transactions on your account");
        }else{
            for(int i = 0; i < account.transactions.size(); i++){
                if(account.transactions.get(i) > 0){
                    System.out.println("Transaction " + (i+1) + " : You deposited $" +
                            account.transactions.get(i) + " into your account");
                }else{
                    System.out.println("Transaction " + (i+1) + " : You withdrew $" +
                            account.transactions.get(i)*(-1) + " from your account");
                }
            }
        }
    }

    public static void showAvailableBalance(BankAccount account){
        System.out.println("You have an available balance of " + account.balance + " in your account");
    }
}