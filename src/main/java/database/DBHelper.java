package database;

import model.User;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {

    private final Connection conn;

    public DBHelper(final Connection connection) throws NullPointerException {
        if(connection == null)
            throw new NullPointerException();
        this.conn = connection;
    }

    /*******************************
     QUERIES
     *******************************/

    public User getOneUser(String login, String password) {
        User user = null;
        String query = "SELECT * FROM \"User\" WHERE \"login\" = '"+login+"' and \"password\" = '"+password+"'";

        try(QueryResult que = getRows(query)) {

            if(que != null) {
                ResultSet rs = que.get();

                while(rs.next()) {
                    user = parseUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getOneUser(String id) {
        User user = null;
        String query = "SELECT * FROM \"User\" WHERE \"ID\" = '"+id+"'";

        try(QueryResult que = getRows(query)) {

            if(que != null) {
                ResultSet rs = que.get();

                while(rs.next()) {
                    user = parseUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return user;
        }
        return user;
    }

    public long getOneUserID(String login, String password) {
        String query = "SELECT \"ID\" FROM \"User\" WHERE \"login\" = '"+login+"' and \"password\" = '"+password+"'";
        long id = -1;

        try(QueryResult que = getRows(query)) {

            if(que != null) {
                ResultSet rs = que.get();

                while(rs.next()) {
                    id = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String addOneUser(String login, String password, String name, String surname, String email) {
        String query = "INSERT INTO \"User\" (\"login\", \"password\", \"name\", \"surname\", \"email\", \"active\") " +
                "VALUES ('"+login+"', '"+password+"', '"+name+"', '"+surname+"', '"+email+"', 'false')";

        String[] tab = {"login", "password", "name", "surname", "email"};

        try (Statement statement = conn.createStatement()){
            statement.executeUpdate(query);
        } catch (SQLException e){

            for (int i = 0; i < tab.length; i++){
                String s = tab[i];

                if(s.equals(e.getLocalizedMessage().contains("login") ? "login" : false)) {
                    return "login";
                } else if (s.equals(e.getLocalizedMessage().contains("email") ? "email" : false)){
                    return "email";
                } else if (s.equals(e.getLocalizedMessage().contains("surname") ? "surname" : false)){
                    return "surname";
                }else if (s.equals(e.getLocalizedMessage().contains("name") ? "name" : false)){
                    return "name";
                } else if (s.equals(e.getLocalizedMessage().contains("password") ? "password" : false)){
                    return "password";
                }
            }
        }

        return "";
    }

    public void activeUser(long userID, String token) {
        String query = "INSERT INTO \"pendingUser\" (\"User_ID\", \"User_active_token\") " +
                "VALUES ('"+userID+"', '"+token+"')";

        if(userID > 0) {
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void activeUser(String token) {
        String query = "UPDATE \"User\" SET \"active\" = 'true' WHERE \"ID\" = (SELECT \"User_ID\" FROM \"pendingUser\" WHERE \"User_active_token\" = '"+token+"')";

            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public boolean isActiveUser(long userID) {
        boolean isActive = false;
        String query = "SELECT \"active\" FROM \"User\" WHERE \"ID\" = '"+userID+"'";

        try(QueryResult que = getRows(query)) {

            if(que != null) {
                ResultSet rs = que.get();

                while(rs.next()) {
                    isActive = rs.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isActive;
    }

    public User restoreUser(String email){
        User user = null;
        String query = "SELECT * FROM \"User\" WHERE \"email\" = '"+email+"'";

        try(QueryResult que = getRows(query)) {

            if(que != null) {
                ResultSet rs = que.get();

                while(rs.next()) {
                    user = parseUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return user;
        }
        return user;
    }

    /*******************************
     PARSERS
     *******************************/

    private User parseUser(ResultSet rs) throws SQLException {
        long id 			= rs.getLong("ID");
        String login 		= rs.getString("login");
        String password 	= rs.getString("password");
        String name 	    = rs.getString("name");
        String surname 		= rs.getString("surname");
        String email 	    = rs.getString("email");

        return new User(id, login, password, name, surname, email);
    }


    /*******************************
     DATABASE METHODS
     *******************************/


    private QueryResult getRows(String query) throws SQLException {
        Statement stat = null;
        ResultSet res = null;

        try {
            stat = conn.createStatement();
            res = stat.executeQuery(query);
        }
        catch(SQLException e) {
            e.printStackTrace();
            if(stat != null)
                stat.close();
            return null;
        }

        return new QueryResult(stat, res);
    }

    private static class QueryResult implements Closeable {

        private final Statement statement;
        private final ResultSet results;

        public QueryResult(final Statement statement, final ResultSet results) {
            this.statement = statement;
            this.results = results;
        }

        private ResultSet get() {
            return results;
        }

        public void close() {
            try {
                if(statement != null)
                    statement.close();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
