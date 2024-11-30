import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class BillingSystem {

    private myJDBC jdbc;


    BillingSystem(myJDBC jdbc){
        this.jdbc = jdbc;


    }
    public boolean addPayment(Payment payment) {
        String query = "INSERT INTO PAYMENT (RUID, Fname, Lname, CardNum, ExpiryDate, SecurityCode)\n" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try{
            PreparedStatement statement = jdbc.dbConnect.prepareStatement(query);
            int ruid = payment.getRUID();
            if (ruid != -1) {
                statement.setInt(1, ruid); // RUID
            } else {
                statement.setNull(1, Types.INTEGER); // Set null for RUID if it's invalid
            }
            statement.setString(2,payment.getfName());
            statement.setString(3,payment.getlName());
            statement.setString(4, payment.getCardNum());
            statement.setString(5, payment.getExpiryDate());
            statement.setString(6, payment.getSecurityCode());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e){
            System.out.println("Error adding payment to DB: " + e.getMessage());
        }
        return false;
    }
    public Payment getPaymentFromCard(String card) {
        String query = null;
        PreparedStatement statement = null;
        Payment payment = null;

        try {
            query = "select *\n" +
                    "from PAYMENT AS P\n" +
                    "where P.CardNum = ? ;";
            statement = jdbc.dbConnect.prepareStatement(query);
            statement.setString(1, card);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int paymentID = results.getInt("PaymentID");
                int RUID = results.getInt("RUID");
                String cardNum = results.getString("CardNum");
                String expiryDate = results.getString("ExpiryDate");
                String fName = results.getString("Fname");
                String lName = results.getString("Lname");
                String security = results.getString("SecurityCode");
                payment = new Payment(paymentID,RUID, fName, lName, cardNum, expiryDate, security);

                //System.out.println(name);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payment;
    }

}
