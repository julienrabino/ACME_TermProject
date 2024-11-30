import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Types;

public class TicketController {

    BillingSystem billingSystem;
    private myJDBC jdbc;

    public TicketController(myJDBC myJDBC){
        this.jdbc = myJDBC;
        billingSystem = new BillingSystem(jdbc);
    }




    public BillingSystem getBillingSystem(){
        return billingSystem;
    }




    public void sendTicketReceipt(Ticket ticket) {
        ticket.sendTicketReceipt(ticket);
    }

    // TicketDatabaseManager functionality directly inside TicketController
    public ArrayList<Ticket> getTickets() {
        ResultSet results;
        ArrayList<Ticket> tickets = new ArrayList<>();

        try {
            Statement myStmt = jdbc.dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM TICKET AS T ;");

            while (results.next()) {
                int ticketID = results.getInt("TicketID");
                int showtimeID = results.getInt("ShowtimeID");
                int RUID = results.getInt("RUID");
                int paymentID = results.getInt("PaymentID");
                double cost = results.getDouble("Cost");
                String email = results.getString("Email");
                String time = results.getString("TimePurchased");
                String date = results.getString("DatePurchased");
                int refund = results.getInt("Refunded");
                int seatID = results.getInt("SeatID");
                boolean refunded = false;
                if (refund == 0) {
                    refunded = false;
                } else {
                    refunded = true;
                }
                Ticket ticket = new Ticket(ticketID, showtimeID, seatID, RUID, email, cost, paymentID, refunded);
                tickets.add(ticket);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tickets;
    }

    public boolean addTicket(Ticket ticket) {
        // Takes ticket and saves it on DB
        // If successful, return TRUE
        String query = "INSERT INTO TICKET (RUID, ShowtimeID, SeatID, Cost, PaymentID, Email, TimePurchased, DatePurchased, Refunded) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ZoneId calgaryTimeZone = ZoneId.of("America/Edmonton"); // Calgary is in the "America/Edmonton" zone

        // Get the current time in Calgary
        ZonedDateTime calgaryCurrentTime = ZonedDateTime.now(calgaryTimeZone);

        // Extract the time (HH:MM:SS) from the ZonedDateTime
        LocalTime localTime = calgaryCurrentTime.toLocalTime();

        // Format the time in "HH:MM:SS" format (compatible with MySQL TIME type)
        String formattedTime = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.toString();  // toString() gives YYYY-MM-DD

        // Get RegisteredUser and Payment info
        RegisteredUser RU = ticket.getRU();
        Payment payment = ticket.getPayment();


        try {
            // Prepare the statement
            PreparedStatement statement = jdbc.dbConnect.prepareStatement(query);

            // Set parameters in the correct order
            if (RU != null && RU.getID() != -1) {
                statement.setInt(1, RU.getID()); // RUID
            } else {
                statement.setNull(1, Types.INTEGER); // Set null for RUID if it's invalid
            }

            statement.setInt(2, ticket.getShowtime().getShowtimeID()); // ShowtimeID
            statement.setInt(3, ticket.getSeat().getSeatID()); // SeatID
            statement.setDouble(4, ticket.getTicketPrice()); // Cost
            if (payment != null) {
                statement.setInt(5, payment.getPaymentID()); // PaymentID
            } else {
                statement.setNull(5, Types.INTEGER); // Set null for PaymentID if payment is not available
            }
            statement.setString(6, ticket.getEmail()); // Email
            statement.setString(7, formattedTime); // TimePurchased
            statement.setString(8, formattedDate); // DatePurchased
            statement.setInt(9, 0); // Refunded (0 for not refunded, 1 for refunded)

            // Execute the query
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            System.out.println("Error adding ticket to DB: " + e.getMessage());
        }

        return false; // Return false if there was an error
    }





    public void changeSeatAvailability(Seat seat, boolean available) {
        int seatID = seat.getSeatID();
        String query = null;
        PreparedStatement statement = null;

        try {
            // Update query that uses the 'available' parameter
            query = "UPDATE SEAT SET Available = ? WHERE SeatID = ?;";

            // Prepare the statement
            statement = jdbc.dbConnect.prepareStatement(query);

            // Set the parameters
            statement.setBoolean(1, available);  // Set the 'Available' value
            statement.setInt(2, seatID);  // Set the SeatID for which you want to update availability

            // Execute the update query
            int rowsAffected = statement.executeUpdate();  // Use executeUpdate for non-SELECT queries

            if (rowsAffected > 0) {
                System.out.println("Seat availability updated successfully.");
            } else {
                System.out.println("No rows affected.");
            }

            // Close the statement
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




//    public boolean addTicketToDB(Ticket ticket) {
//        // takes ticket and saves it on DB
//        // if successful, return TRUE
//        String query = "INSERT INTO tickets (ticketID, userID, showtimeID, seat, ticketPrice, paymentMethod, isRUSeat) VALUES (?, ?, ?, ?, ?, ?, ?)";
//
//
//        try{
//            PreparedStatement statement = jdbc.dbConnect.prepareStatement(query);
//            statement.setInt(3, ticket.getShowtime().getShowtimeID());
//            statement.setInt(4, ticket.getSeat().getSeatID());
//            statement.setDouble(5, ticket.getTicketPrice());
//            statement.setString(6, ticket.getPaymentMethod());
//            statement.setBoolean(7, ticket.getIsAnRUSeat());
//
//            if (!ticket.getUser().getRegistered()){
//                statement.setNull(2, java.sql.Types.INTEGER);
//            } else{
//                statement.setInt(2, ((RegisteredUser)ticket.getUser()).getID());
//
//            }
//            int rowsAffected = statement.executeUpdate();
//            return rowsAffected > 0;
//
//        } catch (SQLException e){
//            System.out.println("Error adding ticket to DB: " + e.getMessage());
//
//
//        }
//
//
//        return false;
//    }

    public boolean updateTicketStatus(Ticket ticket) {
        // uses the ticketStatus attribute of ticket to update the ticketStatus on DB
        // if successful, return TRUE
        String query = "UPDATE tickets SET status = ? WHERE ticketID = ?";

        try {PreparedStatement statement = jdbc.dbConnect.prepareStatement(query);

            statement.setBoolean(1, ticket.getTicketStatus());
            statement.setInt(2, ticket.getTicketID());

            int rowsAffected = statement.executeUpdate();
            return (rowsAffected > 0);

        } catch (SQLException e) {
            System.out.println("Error updating ticket status: " + e.getMessage());
            return false;
        }
    }

    public int produceTicketID() {
        // takes max ticket ID from the ticket table from DB and increments it by 1
        // returns this int
        // wait i think this could also be done in the databse but idk which way we wanna implememnt this
        // -->  we can but i was jus thinking for addTicketToDB the ticket object should alr have an id before insertion ahhh
        return 0;
    }

    public boolean fetchCancellationEligibility(Ticket ticket) {
        String query = "SELECT cancellationEligibility FROM tickets WHERE ticketID = ?";
        try{
            PreparedStatement statement = jdbc.dbConnect.prepareStatement(query);
            statement.setInt(1, ticket.getTicketID());
            ResultSet result = statement.executeQuery();
            while (result.next()){
                return result.getBoolean("cancellationEligibility"); // assuming this is the column name
            }
        }catch(SQLException e) {
            System.out.println("Error fetching cancellation eligibility: " + e.getMessage());
        }
        return false;

    }

    public Ticket fetchTicket(int ticketID) {
        // queries DB for existing ticket info, based on ticketID
        // using this info, make and return a Ticket object
        // may have to call fetchShowtime to make the showtime object and call fetchUser if registered

        String query = "SELECT * FROM tickets WHERE ticketID = ?";
        int userID = -1;
        User user = null;
        try{
            PreparedStatement statement = jdbc.dbConnect.prepareStatement(query);
            statement.setInt(1, ticketID);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                int id = result.getInt("ticketID");
                int showtimeID = result.getInt("showtimeID");
                userID = result.getInt("userID"); // could be null for OUs, need an if-else statement
                double price = result.getDouble("price");
                // !!! still need to query the seat !!
                String paymentMethod = result.getString("paymentMethod");
                boolean isAnRUSeat = result.getBoolean("isRUSeat");
                if (userID == -1){
                    // an OU booked this ticket
                     user = new User(); // default ctor for now, not sure if we're gonna save basic OU info for transactions (like email and name)
                                        // like maybe have a transaction/payment table in db?
                                        // if we do, can query BillingSystem to get user email even if theyre OU
                } else{
                    UserDatabaseManager userDBM = new UserDatabaseManager(jdbc);
                    user = userDBM.fetchUser(userID);
                }
                MovieTheatreController movieTC = new MovieTheatreController(jdbc);
              //  Showtime showtime = MovieTheatreController.fetchShowtime(showtimeID);
                //return new Ticket(id, showtime, user, -1, price, paymentMethod, isAnRUSeat);

            }

        }catch(SQLException e){
            System.out.println("Error fetching ticket: " + e.getMessage());

        }
        return null;


    }


}
