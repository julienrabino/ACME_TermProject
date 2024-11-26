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
    //careful idk if below getPassword function works !
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
