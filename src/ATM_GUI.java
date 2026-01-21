import javax.swing.*;
import java.awt.*;
import com.acc.dao.AccountDAO;

public class ATM_GUI {

    AccountDAO dao = new AccountDAO();
    OptionMenu op = new OptionMenu();   // backend logic (formatting, flow)

    JFrame frame;
    JTextField customerField, amountField;
    JPasswordField pinField;

    int currentUser = -1;

    ATM_GUI() {
        showLoginScreen();
    }

    // ================= LOGIN SCREEN =================
    void showLoginScreen() {
        frame = new JFrame("ATM System");
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("ATM MACHINE");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(130, 20, 200, 30);

        JLabel cl = new JLabel("Customer No:");
        cl.setBounds(50, 80, 100, 25);
        customerField = new JTextField();
        customerField.setBounds(160, 80, 150, 25);

        JLabel pl = new JLabel("PIN:");
        pl.setBounds(50, 120, 100, 25);
        pinField = new JPasswordField();
        pinField.setBounds(160, 120, 150, 25);

        JButton login = new JButton("Login");
        login.setBounds(140, 180, 100, 30);

        JButton create = new JButton("Create Account");
        create.setBounds(60, 220, 130, 30);

        JButton delete = new JButton("Delete Account");
        delete.setBounds(210, 220, 130, 30);

        frame.add(title);
        frame.add(cl);
        frame.add(customerField);
        frame.add(pl);
        frame.add(pinField);
        frame.add(login);
        frame.add(create);
        frame.add(delete);

        login.addActionListener(e -> login());
        create.addActionListener(e -> createAccountGUI());
        delete.addActionListener(e -> deleteAccountGUI());

        frame.setVisible(true);
    }

    void login() {
        try {
            int cn = Integer.parseInt(customerField.getText());
            int pin = Integer.parseInt(new String(pinField.getPassword()));

            if (dao.validateLogin(cn, pin)) {
                currentUser = cn;
                op.setCustomerNumber(cn);   // sync backend
                frame.dispose();
                showAccountTypeScreen();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Login");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Enter valid numbers");
        }
    }

    // ================= ACCOUNT TYPE SCREEN =================
    void showAccountTypeScreen() {
        frame = new JFrame("Select Account");
        frame.setSize(400, 250);
        frame.setLayout(null);

        JButton current = new JButton("Current Account");
        JButton saving = new JButton("Saving Account");
        JButton logout = new JButton("Logout");

        current.setBounds(100, 40, 200, 30);
        saving.setBounds(100, 90, 200, 30);
        logout.setBounds(100, 140, 200, 30);

        frame.add(current);
        frame.add(saving);
        frame.add(logout);

        current.addActionListener(e -> {
            frame.dispose();
            showCurrentMenu();
        });

        saving.addActionListener(e -> {
            frame.dispose();
            showSavingMenu();
        });

        logout.addActionListener(e -> {
            frame.dispose();
            showLoginScreen();
        });

        frame.setVisible(true);
    }

    // ================= CURRENT ACCOUNT =================
    void showCurrentMenu() {
        frame = new JFrame("Current Account");
        frame.setSize(400, 300);
        frame.setLayout(null);

        JButton balance = new JButton("Balance");
        JButton withdraw = new JButton("Withdraw");
        JButton deposit = new JButton("Deposit");
        JButton back = new JButton("Back");

        balance.setBounds(100, 30, 200, 30);
        withdraw.setBounds(100, 80, 200, 30);
        deposit.setBounds(100, 130, 200, 30);
        back.setBounds(100, 180, 200, 30);

        frame.add(balance);
        frame.add(withdraw);
        frame.add(deposit);
        frame.add(back);

        balance.addActionListener(e -> {
            double bal = dao.getCurrentBalance(currentUser);
            JOptionPane.showMessageDialog(frame,
                    "Current Balance : " + op.df1.format(bal));
        });

        withdraw.addActionListener(e -> transactionScreen(true, true));
        deposit.addActionListener(e -> transactionScreen(false, true));

        back.addActionListener(e -> {
            frame.dispose();
            showAccountTypeScreen();
        });

        frame.setVisible(true);
    }

    // ================= SAVING ACCOUNT =================
    void showSavingMenu() {
        frame = new JFrame("Saving Account");
        frame.setSize(400, 300);
        frame.setLayout(null);

        JButton balance = new JButton("Balance");
        JButton withdraw = new JButton("Withdraw");
        JButton deposit = new JButton("Deposit");
        JButton back = new JButton("Back");

        balance.setBounds(100, 30, 200, 30);
        withdraw.setBounds(100, 80, 200, 30);
        deposit.setBounds(100, 130, 200, 30);
        back.setBounds(100, 180, 200, 30);

        frame.add(balance);
        frame.add(withdraw);
        frame.add(deposit);
        frame.add(back);

        balance.addActionListener(e -> {
            double bal = dao.getSavingBalance(currentUser);
            JOptionPane.showMessageDialog(frame,
                    "Saving Balance : " + op.df2.format(bal));
        });

        withdraw.addActionListener(e -> transactionScreen(true, false));
        deposit.addActionListener(e -> transactionScreen(false, false));

        back.addActionListener(e -> {
            frame.dispose();
            showAccountTypeScreen();
        });

        frame.setVisible(true);
    }

    // ================= TRANSACTION SCREEN =================
    void transactionScreen(boolean isWithdraw, boolean isCurrent) {
        JFrame tFrame = new JFrame("Transaction");
        tFrame.setSize(300, 200);
        tFrame.setLayout(null);

        JLabel label = new JLabel("Enter Amount:");
        label.setBounds(30, 30, 100, 25);

        amountField = new JTextField();
        amountField.setBounds(140, 30, 100, 25);

        JButton submit = new JButton("Submit");
        submit.setBounds(90, 80, 100, 30);

        tFrame.add(label);
        tFrame.add(amountField);
        tFrame.add(submit);

        submit.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(amountField.getText());
                boolean success;

                if (isCurrent) {
                    success = isWithdraw
                            ? dao.withdrawCurrent(currentUser, amt)
                            : dao.depositCurrent(currentUser, amt);
                } else {
                    success = isWithdraw
                            ? dao.withdrawSaving(currentUser, amt)
                            : dao.depositSaving(currentUser, amt);
                }

                if (success)
                    JOptionPane.showMessageDialog(tFrame, "Transaction Successful");
                else
                    JOptionPane.showMessageDialog(tFrame, "Transaction Failed");

                tFrame.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(tFrame, "Invalid Amount");
            }
        });

        tFrame.setVisible(true);
    }

    // ================= CREATE ACCOUNT =================
    void createAccountGUI() {

        JTextField cnField = new JTextField();
        JPasswordField pinField = new JPasswordField();

        Object[] message = {
                "Customer Number:", cnField,
                "PIN:", pinField
        };

        int option = JOptionPane.showConfirmDialog(
                frame, message, "Create Account",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int cn = Integer.parseInt(cnField.getText());
                int pin = Integer.parseInt(new String(pinField.getPassword()));

                if (dao.createAccount(cn, pin))
                    JOptionPane.showMessageDialog(frame, "Account Created Successfully!");
                else
                    JOptionPane.showMessageDialog(frame, "Customer already exists!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Enter valid numbers only!");
            }
        }
    }

    // ================= DELETE ACCOUNT =================
    void deleteAccountGUI() {

        JTextField cnField = new JTextField();
        JPasswordField pinField = new JPasswordField();

        Object[] message = {
                "Customer Number:", cnField,
                "PIN:", pinField
        };

        int option = JOptionPane.showConfirmDialog(
                frame, message, "Delete Account",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int cn = Integer.parseInt(cnField.getText());
                int pin = Integer.parseInt(new String(pinField.getPassword()));

                if (dao.deleteAccount(cn, pin))
                    JOptionPane.showMessageDialog(frame, "Account Deleted Successfully!");
                else
                    JOptionPane.showMessageDialog(frame, "Invalid Customer Number or PIN!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Enter valid numbers only!");
            }
        }
    }

    public static void main(String[] args) {
        new ATM_GUI();
    }
}
