/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSource;

import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Boyko
 */
public class UserMapper {

    //Save
    public boolean saveAccountInformationIntoLoginTables(ArrayList<User> userList, Connection DBConnect) {
        int rowsInserted = 0;

        String sqlStringInsert;
        PreparedStatement statement = null;

        try {

            for (int i = 0; i < userList.size(); i++) {
                User saveAdministrator = userList.get(i);
                if (userList.get(i).getUsername().contains("cl_")) {
                    sqlStringInsert = "insert into Guest_login values (?,?,?)";
                } else {
                    sqlStringInsert = "insert into Employee_login values (?,?,?)";
                }
                statement = DBConnect.prepareStatement(sqlStringInsert);
                statement.setString(1, saveAdministrator.getUsername());
                statement.setString(2, saveAdministrator.getPassword());
                statement.setInt(3, saveAdministrator.getPersonal_no());
                rowsInserted = statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Fail in saveAccountInformationIntoEmployeeLoginTable");
            System.out.println(e.getMessage());
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Fail in saveAccountInformationIntoEmployeeLoginTable");
                System.out.println(e.getMessage());
            }
        }
        return rowsInserted == 1;
    }

    //Load 
    //Single show Field
    public User getAccountInformationFromLoginTables(String username, Connection DBConnection) {
        User currentUser = null;
        String SQLString = null;
        PreparedStatement statement = null;
        try {
            if (username.contains("cl_")) {
                SQLString = "select * from Guest_login where user_name = ?";
            } else if (username.contains("emp_")){
                SQLString = "select * from Employee_Login where user_name = ?";
            }
            statement = DBConnection.prepareStatement(SQLString);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                currentUser = new User(username, rs.getString(2), rs.getInt(3));
            }
        } catch (SQLException e) {
            System.out.println("Fail in getAccountInformationFromLoginTables");
            System.out.println(e.getMessage());
        } finally // must close statement
        {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("Fail in getAccountInformationFromLoginTables");
                System.out.println(e.getMessage());
            }
        }
        return currentUser;
    }

    //All Login Info
    public ArrayList<User> getAllInformationFromEmployeeLoginTable(Connection dBConnection) {
        User user = null;
        ArrayList<User> usersArray = new ArrayList<User>();
        String SQLString1
                = "SELECT * "
                + "FROM Employee_login";
        PreparedStatement statement = null;
        try {
            statement = dBConnection.prepareStatement(SQLString1);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                user = new User(rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3));
                usersArray.add(user);

            }
        } catch (SQLException e) {
            System.out.println("Fail in getAllInformationFromEmployeeLoginTable");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("Fail in getAllInformationFromEmployeeLoginTable");
                System.out.println(e.getMessage());
            }
        }
        return usersArray;
    }

    public ArrayList<User> getAllInformationFromGuestLoginTable(Connection dBConnection) {
        User user = null;
        ArrayList<User> usersArray = new ArrayList<User>();
        String SQLString1 = // get order
                "SELECT * "
                + "FROM guest_login";
        PreparedStatement statement = null;
        try {
            statement = dBConnection.prepareStatement(SQLString1);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                user = new User(rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3));
                usersArray.add(user);

            }
        } catch (SQLException e) {
            System.out.println("Fail in getAllInformationFromEmployeeLoginTable");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("Fail in getAllInformationFromEmployeeLoginTable");
                System.out.println(e.getMessage());
            }
        }
        return usersArray;
    }

    //Delete
    public boolean deleteEmployeeAccount(ArrayList<String> usersArray, Connection DBConnection) {
        int rowsDeleted = 0;
        for (int i = 0; i < usersArray.size(); i++) {

            User user = getAccountInformationFromLoginTables(usersArray.get(i), DBConnection);
            String SQLString1;
            PreparedStatement statement = null;

            try {
                if (usersArray.get(i).contains("cl_")) {
                    SQLString1 = "delete from Guest_login where user_name = ?";
                } else {
                    SQLString1 = "delete from Employee_login where user_name = ?";
                }
                statement = DBConnection.prepareStatement(SQLString1);
                statement.setString(1, user.getUsername());

                rowsDeleted = statement.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Fail in deleteEmployeeAccount");
                System.out.println(e.getMessage());
            } finally 
            {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Fail in deleteEmployeeAccount");
                    System.out.println(e.getMessage());
                }
            }
        }
        return rowsDeleted == usersArray.size();
    }
}
