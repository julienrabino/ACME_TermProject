import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.regex.Pattern;

public class BillingSystem {

    private myJDBC jdbc;
    private MovieTheatreApp app;


    BillingSystem(myJDBC jdbc, MovieTheatreApp app){
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

    public boolean validatePayment(Payment payment, String email) {
        String cardNum = payment.getCardNum();
        boolean valid = true;
        String cardPattern = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";
        if (!cardNum.matches(cardPattern)) {
            JOptionPane.showMessageDialog(app, "Invalid card number. Card number must be in form 'xxxx-xxxx-xxxx-xxxx'");
            System.out.println("Invalid card number.");
            valid = false;
        }

        String expiryDate = payment.getExpiryDate();
        String datePattern = "(0[1-9]|1[0-2])/\\d{2}";
        if (!expiryDate.matches(datePattern) ) {
            JOptionPane.showMessageDialog(app, "Invalid expiry date.");
            System.out.println("Invalid expiry date .");
            valid = false;        }

        String securityCode = payment.getSecurityCode();
        String securityCodePattern = "\\d{3}";
        if (!securityCode.matches(securityCodePattern)) {
            JOptionPane.showMessageDialog(app, "Invalid security code. Must be 3 digits (e.g. 123)");
            System.out.println("Invalid security code .");
            valid = false;        }
        String emailRegex = "^[a-zA-Z0-9_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.matches(emailRegex, email)) {
            JOptionPane.showMessageDialog(app, "Invalid email format. Only letters, numbers, '_', '.', and '@' are allowed.");
            valid = false;        }

        return valid;
    }


}
