package io.ian;

import io.ian.helpers.DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");{
            Scanner scanner = new Scanner(System.in);

            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:atm.db")) {
                DatabaseManager dbm = new DatabaseManager(conn);
                while (true) {
                    System.out.println("1 - Deposit");
                    System.out.println("2 - Withdraw");
                    System.out.println("3 - View Balance");
                    System.out.println("4 - Quit");
                    Integer choice = scanner.nextInt();
                    if (choice == 1) {
                        System.out.println("Input user ID");
                        Integer id = scanner.nextInt();
                        System.out.println("Input amount to deposit");
                        Double amount = scanner.nextDouble();
                        dbm.depositFunds(id, amount);
                    } else if (choice == 2) {
                        System.out.println("Input user ID");
                        Integer id = scanner.nextInt();
                        System.out.println("Input amount to withdraw");
                        Double amount = scanner.nextDouble();
                        dbm.withdrawFunds(id, amount);
                    } else if (choice == 3) {
                        System.out.println("Input user ID");
                        Integer id = scanner.nextInt();
                        dbm.viewFunds(id);
                    } else if (choice == 4){
                        break;
                    } else {
                        System.out.println("Invalid input");
                    }
                }
            } catch (SQLException ex) {
                System.out.println("We encountered a problem talking to the database");
            }
            System.out.println("we good");
        }
    }
}
