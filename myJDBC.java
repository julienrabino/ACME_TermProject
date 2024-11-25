import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class myJDBC {
    public final String DBURL;
    public final String USERNAME;
    public final String PASSWORD;
    public Connection dbConnect;
    protected ResultSet results;

    public myJDBC(String dburl, String username, String password) {
        this.DBURL = dburl;
        this.USERNAME = username;
        this.PASSWORD = password;
    }
    public myJDBC() {
        this.DBURL = "jdbc:mysql://localhost:3306/MOVIE_THEATRE";
        this.USERNAME = "root";
        this.PASSWORD = "123";
    }

    public void initializeConnection() {
        try {
            // Explicitly load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            this.dbConnect = DriverManager.getConnection(this.DBURL, this.USERNAME, this.PASSWORD);
            System.out.println("Connection to the database successful!");

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Please make sure the JDBC driver is properly added to the classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }


    String getDburl() {
        return this.DBURL;
    }

    String getUsername() {
        return this.USERNAME;
    }

    String getPassword() {
        return this.PASSWORD;
    }



    // returns the movie IDs
//    public ArrayList<Integer> searchMovie(String search) {
//        ArrayList<Integer> IDs = new ArrayList<>();
//
//        try {
//            Statement myStmt = dbConnect.createStatement();
//            results = myStmt.executeQuery("select M.MovieID, M.Title\n" +
//                    "from MOVIE as M\n" +
//                    "where LOCATE(LOWER('" + search + "'), LOWER(M.Title)) > 0;");
//
//            System.out.println("Results: ");
//            while (results.next()) {
//                System.out.println("ID: " + results.getInt("MovieID") + "     Name: " +
//                        results.getString("Title"));
//                IDs.add(new Integer(results.getInt("MovieID")));
//            }
//
//            myStmt.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return IDs;
//    }
}



