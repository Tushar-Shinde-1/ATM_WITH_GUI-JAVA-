package com.acc.dao;

import com.acc.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    // 🔐 LOGIN VALIDATION
    public boolean validateLogin(int customerNo, int pin) {

        String sql = "SELECT 1 FROM accounts WHERE customer_no=? AND pin=?";

        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerNo);
            ps.setInt(2, pin);

            ResultSet rs = ps.executeQuery();
            return rs.next();   // true if user exists

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ➕ CREATE ACCOUNT
    public boolean createAccount(int customerNo, int pin) {

        String sql = "INSERT INTO accounts (customer_no, pin) VALUES (?, ?)";

        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerNo);
            ps.setInt(2, pin);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println("Account already exists!");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ❌ DELETE ACCOUNT
    public boolean deleteAccount(int customerNo, int pin) {

        String sql = "DELETE FROM accounts WHERE customer_no=? AND pin=?";

        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerNo);
            ps.setInt(2, pin);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 💰 GET CURRENT BALANCE
    public double getCurrentBalance(int customerNo) {

        String sql = "SELECT current_balance FROM accounts WHERE customer_no=?";

        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("current_balance");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 💰 GET SAVING BALANCE
    public double getSavingBalance(int customerNo) {

        String sql = "SELECT saving_balance FROM accounts WHERE customer_no=?";

        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("saving_balance");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ➖ WITHDRAW FROM CURRENT ACCOUNT
    public boolean withdrawCurrent(int customerNo, double amount) {

        String sql =
            "UPDATE accounts " +
            "SET current_balance = current_balance - ? " +
            "WHERE customer_no = ? AND current_balance >= ?";

        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setInt(2, customerNo);
            ps.setDouble(3, amount);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ➕ DEPOSIT TO CURRENT ACCOUNT
    public boolean depositCurrent(int customerNo, double amount) {

        String sql =
            "UPDATE accounts " +
            "SET current_balance = current_balance + ? " +
            "WHERE customer_no = ?";

        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setInt(2, customerNo);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ➖ WITHDRAW FROM SAVING ACCOUNT
    public boolean withdrawSaving(int customerNo, double amount) {

        String sql =
            "UPDATE accounts " +
            "SET saving_balance = saving_balance - ? " +
            "WHERE customer_no = ? AND saving_balance >= ?";

        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setInt(2, customerNo);
            ps.setDouble(3, amount);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ➕ DEPOSIT TO SAVING ACCOUNT
    public boolean depositSaving(int customerNo, double amount) {

        String sql =
            "UPDATE accounts " +
            "SET saving_balance = saving_balance + ? " +
            "WHERE customer_no = ?";

        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setInt(2, customerNo);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
