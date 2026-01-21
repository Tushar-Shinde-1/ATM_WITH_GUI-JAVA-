

import java.util.*;
import java.text.*;
import com.acc.dao.AccountDAO;



class OptionMenu extends Account {

    Scanner sc = new Scanner(System.in);
    AccountDAO dao = new AccountDAO();

    void getLogin() {
        do {
            try {
                System.out.println("\nWelcome to the ATM");
                System.out.println("1 : Login");
                System.out.println("2 : Create Account");
                System.out.println("3 : Delete Account");
                System.out.print("Choice : ");

                int choice = sc.nextInt();

                switch (choice) {

                case 1:
                    System.out.print("Enter Customer Number : ");
                    int cn = sc.nextInt();

                    System.out.print("Enter the PIN-Number : ");
                    int pin = sc.nextInt();

                    if (dao.validateLogin(cn, pin)) {
                        setCustomerNumber(cn);   // store logged-in user
                        getAccountType();
                    } else {
                        System.err.println("Invalid Customer Number OR PIN");
                    }
                    break;

                case 2:
                    registerAccount();
                    break;

                case 3:
                    deleteAccount();
                    break;

                default:
                    System.err.println("Invalid Choice");
                }

            } catch (InputMismatchException e) {
                System.err.println("Enter numbers only!");
                sc.next();
            }
        } while (true);
    }

    void registerAccount() {
        System.out.println("\n--- Register New Account ---");
        System.out.print("Enter New Customer Number : ");
        int cn = sc.nextInt();

        System.out.print("Enter New PIN : ");
        int pin = sc.nextInt();

        if (dao.createAccount(cn, pin)) {
            System.out.println("Account Registered Successfully!");
        } else {
            System.err.println("Customer already exists!");
        }
    }

    void deleteAccount() {
        System.out.println("\n--- Delete Account ---");
        System.out.print("Enter Customer Number : ");
        int cn = sc.nextInt();

        System.out.print("Enter PIN : ");
        int pin = sc.nextInt();

        if (dao.deleteAccount(cn, pin)) {
            System.out.println("Account Deleted Successfully!");
        } else {
            System.err.println("Invalid Customer Number or PIN!");
        }
    }

    void getAccountType() {
        System.out.println("\n<--- Account Type --->");
        System.out.println("1 : Current Account");
        System.out.println("2 : Saving Account");
        System.out.println("3 : Exit");
        System.out.print("Choice : ");

        int ch = sc.nextInt();

        switch (ch) {
        case 1:
            getCurrent();
            break;
        case 2:
            getSaving();
            break;
        case 3:
            System.out.println("Thank You for Visiting");
            break;
        default:
            System.err.println("Invalid Choice");
        }
    }

    void getCurrent() {
        System.out.println("\n--- CURRENT ACCOUNT ---");
        System.out.println("1 : Balance Enquiry");
        System.out.println("2 : Withdraw");
        System.out.println("3 : Deposit");
        System.out.println("4 : Back");
        System.out.print("Choice : ");

        int ch = sc.nextInt();

        switch (ch) {
        case 1:
            getCurrentBalance();
            break;
        case 2:
            getCurrentWithdrawInput();
            break;
        case 3:
            getCurrentDeposit();
            break;
        case 4:
            getAccountType();
            break;
        default:
            System.err.println("Invalid Choice");
        }
    }

    void getSaving() {
        System.out.println("\n--- SAVING ACCOUNT ---");
        System.out.println("1 : Balance Enquiry");
        System.out.println("2 : Withdraw");
        System.out.println("3 : Deposit");
        System.out.println("4 : Back");
        System.out.print("Choice : ");

        int ch = sc.nextInt();

        switch (ch) {
        case 1:
            getSavingBalance();
            break;
        case 2:
            getSavingWithdrawInput();
            break;
        case 3:
            getSavingDeposit();
            break;
        case 4:
            getAccountType();
            break;
        default:
            System.err.println("Invalid Choice");
        }
    }
}


class Account {

    Scanner sc = new Scanner(System.in);
    AccountDAO dao = new AccountDAO();

    DecimalFormat df1 = new DecimalFormat("###,##0.00' Rupees'");
    DecimalFormat df2 = new DecimalFormat("###,##0.00' Ruppes'");

    private int customerNumber;
    private int pin;

    void setCustomerNumber(int cn) {
        this.customerNumber = cn;
    }

    int getCustomerNumber() {
        return customerNumber;
    }

    void setPINNumber(int pn) {
        this.pin = pn;
    }

    int getPINNumber() {
        return pin;
    }

    // -------- CURRENT ACCOUNT --------

    void getCurrentBalance() {
        double bal = dao.getCurrentBalance(getCustomerNumber());
        System.out.println("Current Balance : " + df1.format(bal));
    }

    void getCurrentWithdrawInput() {
        System.out.print("Enter Withdraw Amount : ");
        double amount = sc.nextDouble();

        if (dao.withdrawCurrent(getCustomerNumber(), amount)) {
            System.out.println("Transaction Successful");
            getCurrentBalance();
        } else {
            System.out.println("Insufficient Balance");
        }
    }

    void getCurrentDeposit() {
        System.out.print("Enter Deposit Amount : ");
        double amount = sc.nextDouble();

        if (dao.depositCurrent(getCustomerNumber(), amount)) {
            System.out.println("Transaction Successful");
            getCurrentBalance();
        } else {
            System.out.println("Transaction Failed");
        }
    }

    // -------- SAVING ACCOUNT --------

    void getSavingBalance() {
        double bal = dao.getSavingBalance(getCustomerNumber());
        System.out.println("Saving Balance : " + df2.format(bal));
    }

    void getSavingWithdrawInput() {
        System.out.print("Enter Withdraw Amount : ");
        double amount = sc.nextDouble();

        if (dao.withdrawSaving(getCustomerNumber(), amount)) {
            System.out.println("Transaction Successful");
            getSavingBalance();
        } else {
            System.out.println("Insufficient Balance");
        }
    }

    void getSavingDeposit() {
        System.out.print("Enter Deposit Amount : ");
        double amount = sc.nextDouble();

        if (dao.depositSaving(getCustomerNumber(), amount)) {
            System.out.println("Transaction Successful");
            getSavingBalance();
        } else {
            System.out.println("Transaction Failed");
        }
    }
}

public class ATM {
    public static void main(String[] args) {
        OptionMenu op = new OptionMenu();
        op.getLogin();
    }
}
