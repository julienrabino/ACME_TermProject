import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class UserDatabaseManager {
    private myJDBC jdbc;

    UserDatabaseManager(myJDBC db){
        this.jdbc = db;
    }

    public void insertRU(RegisteredUser RU) {
        String Fname = RU.getFname();
        String Lname = RU.getFname();
        String usr = RU.getUsername();
        String pw = RU.getPassword();
        String email = RU.getEmail();
        String address = RU.getAddress();
        LocalDate joinDate = RU.getJoinDate();
        LocalDate expiryDate = RU.getMembershipExpiry();
        boolean SP = RU.getPaymentMethodSaved();
        int savedPayment;
        if (SP) {
            savedPayment = 1;
        }
        else {
            savedPayment = 0;
        }

        try {
            String query = "INSERT INTO REG_USER (Fname, Lname, Email, Username, Password1, Address, MemberShipJoinDate, MemberShipExpiry, SavedPayment)\n" +
                    "VALUES\n" +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement myStmt = jdbc.dbConnect.prepareStatement(query);

            myStmt.setString(1, Fname);
            myStmt.setString(2, Lname);
            myStmt.setString(3, email);
            myStmt.setString(4, usr);
            myStmt.setString(5, pw);
            myStmt.setString(6, address);
            myStmt.setObject(7, joinDate);
            myStmt.setObject(8, expiryDate);
            myStmt.setInt(9, savedPayment);

            int rowCount = myStmt.executeUpdate();
            System.out.println("Rows affected: " + rowCount);

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    boolean usernameExists(String usr) {
        String query = null;
        PreparedStatement statement = null;
        int ID = -1;

        try {
            query = "select RU.ID\n" +
                    "from REG_USER AS RU\n" +
                    "where RU.username = ? ;";
            statement = jdbc.dbConnect.prepareStatement(query);
            statement.setString(1, usr);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                ID = results.getInt("ID");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(ID != -1) {
            return true;
        }
        else{
            return false;
        }
    }
    String getPassword(RegisteredUser RU) {
        String query = null;
        PreparedStatement statement = null;
        int ID = RU.getID();
        String pw = "";

        try {
            query = "select RU.Password1\n" +
                    "from REG_USER AS RU\n" +
                    "where RU.ID = ? ;";
            statement = jdbc.dbConnect.prepareStatement(query);
            statement.setInt(1, ID);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                pw = results.getString("Password1");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pw;
    }

    RegisteredUser getRUFromUsername(String usr) {
        String query = null;
        RegisteredUser RU = null;
        PreparedStatement statement = null;

        try {
            query = "select RU.ID, RU.Fname, RU.Lname, RU.Username, RU.Password1, RU.Email, RU.Address, RU.MemberShipJoinDate, RU.MemberShipExpiry, RU.SavedPayment\n" +
                    "from REG_USER AS RU\n" +
                    "where RU.Username COLLATE utf8mb4_bin = ? ;";
            statement = jdbc.dbConnect.prepareStatement(query);
            statement.setString(1, usr);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int ID = results.getInt("ID");
                String Fname = results.getString("FName");
                String Lname = results.getString("LName");
                String username = results.getString("Username");
                String password = results.getString("Password1");
                String address = results.getString("Address");
                String email = results.getString("Email");
                LocalDate joinDate = results.getObject("MemberShipJoinDate", LocalDate.class);
                LocalDate expiryDate = results.getObject("MemberShipExpiry", LocalDate.class);
                //System.out.println("IN UDBM");
                //System.out.println(password);
                int SP = results.getInt("SavedPayment");
                boolean savedPayment = (SP!=0);
                RU = new RegisteredUser(ID, Fname, Lname,username, password, email, address, expiryDate, joinDate, savedPayment);
                //System.out.println(name);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return RU;
    }


    public RegisteredUser fetchUser(int id){
        // TO DO:
        // query db to find user's details
        // must use db info to make an RU Object, which should be returned
        return null;
    }
}
