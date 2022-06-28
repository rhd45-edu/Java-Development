import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static boolean isBanking;
    public static void main(String[] args) {
        System.out.println("-----Welcome to Phoenix Bank -------");
        System.out.print("Please enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Please enter your password: ");
        String password = scanner.nextLine();

        BankAccount userAccount = DBOperations.getAccountHolder(username, password);

        if (userAccount == null) {
            System.out.println("Authentication failed! Please try again");
        } else {
            System.out.println("Log in Successful!\n");
            isBanking = true;
            while(isBanking){
                printMenu();
                String option = scanner.nextLine();
                double amount;

                switch(option){
                    case "1":
                        System.out.print("How much would you like to withdraw?: ");
                        amount = scanner.nextDouble();
                        scanner.nextLine();
                        BankAccount.withdrawFromAccount(userAccount, amount);
                        //withdrawFromAccount(amount)
                        //Needs to check if the amount specified is in the account
                        //If so, then let the user know the withdrawal was successful and then add withdrawal to transactions
                        //If not, let the user know there are insufficient funds
                        break;

                    case "2":
                        System.out.print("How much would you like to deposit: ");
                        amount = scanner.nextDouble();
                        scanner.nextLine();
                        BankAccount.depositIntoAccount(userAccount, amount);
                        //depositIntoAccount(amount)
                        //Check if the amount is greater than zero
                        //If so, let the user know the deposit was successful by showing new balance, add the amount to the balance
                        //and add amount to the transactions list
                        break;
                    case "3":
                        BankAccount.showAvailableBalance(userAccount);
                        //showAccountBalance();
                        //displays the available balance in the account
                        break;
                    case "4":
                        //showAccountTransactions();
                        //List all Transactions for given account
                        BankAccount.viewAllTransactions(userAccount);
                        break;
                    case "5":
                        //Call the update method that updates the userAccount with the matching db record
                        DBOperations.updateAccountHolder(userAccount, username, password);
                        scanner.close();
                        isBanking = false;
                        break;
                    default:
                        System.out.println("Invalid option selected! Please try again!");
                        break;
                }
            }
        }
    }

    private static void printMenu(){
        System.out.println("Please select one of the following options \n" +
                "1: Make a withdrawal \n" +
                "2: Make a deposit \n" +
                "3: View account balance \n" +
                "4: View all account transactions \n" +
                "5: Log out");
        System.out.print("\nOption: ");
    }

}