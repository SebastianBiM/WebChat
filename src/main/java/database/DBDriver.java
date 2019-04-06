package database;

import model.User;

import java.sql.*;

public class DBDriver {

    private Connection connection;
    private Statement statement;
    private static DBDriver instance;


    private DBDriver() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres","admin");
            System.out.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBDriver getInstance() {
        try {
            if (instance == null) {
                instance = new DBDriver();
            } else if (instance.getConnection().isClosed()) {
                instance = new DBDriver();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public Connection getConnection() {
            return connection;
    }

    public User LogIn(String login, String password) {
        User user = null;
        try {
            user = new DBHelper(connection).getOneUser(login, password);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return user;
    }


    public void CloseConnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
