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

public class TicketController {

    BillingSystem billingSystem;
    private myJDBC jdbc;

    public TicketController(myJDBC myJDBC){
        this.jdbc = myJDBC;
    }


    TicketController() {
        this.billingSystem = new BillingSystem();
    }

//    public Ticket purchaseTicket(Showtime showtime, User user, Seat seat) {
//        //  all methods used here need to be refactored to adapt to the new seat object (it used to be int)
//        double ticketPrice = 13.50;
//
//        Ticket ticket = null;
//
//        int ticketID = produceTicketID();
//
//        // if user is RU, they may attempt to reserve an RU seat, but if unavailable, they may try to reserve an OU seat.
//        if (user.getRegistered()) {
//            if (showtime.updateSeats(true, 1, true)) {
//                //ticket = new Ticket(ticketID, showtime, user, seat, ticketPrice, user.getPaymentMethod(), true);
//            } else if (showtime.updateSeats(false, 1, true)) {
//               // ticket = new Ticket(ticketID, showtime, user, seat, ticketPrice, user.getPaymentMethod(), false);
//            }
//        }
//
//        // if user is OU, they can only try to purchase an OU seat.
//        else if ((!user.getRegistered()) && showtime.updateSeats(false, 1, true)) {
//            //ticket = new Ticket(ticketID, showtime, user, seat, ticketPrice, user.getPaymentMethod(), false);
//        }
//
//        if (ticket == null) {
//            System.out.println("No more seats available for this showtime. Please select another.");
//            return ticket; // for caller: if purchaseTicket() returns null, request User to pick another showtime.
//        }
//
//        //billingSystem.processTicketPayment(user, ticket);
//
//        // TO ADD:
//        // add ticket to DB
//        if (!addTicketToDB(ticket)) {
//            System.out.println("Unable to save ticket to DB.");
//        }
//
//        // save remaining OUSeats for this showtime to DB
//        if (!updateShowtimeSeats(showtime)) {
//            System.out.println("Unable to update seat count in DB.");
//        }
//        return ticket;


    public void cancelTicket(Ticket ticket, User user) {

        boolean cancellationEligibility = fetchCancellationEligibility(ticket); // default


        // query DB if ticket is still eligible for cancellation; reassign cancellationEligibility

        if (cancellationEligibility) {
            //billingSystem.processTicketRefund(ticket, user);
            ticket.cancelTicket();
            if (!updateTicketStatus(ticket)){
                System.out.println("Unable to update ticket status in DB. Ticket may already be cancelled.");
            }
            boolean isAnRUSeat = ticket.getIsAnRUSeat();

            Showtime showtime = ticket.getShowtime();
            //showtime.updateSeats(isAnRUSeat, 1, false);
            if (!updateShowtimeSeats(showtime)){
                System.out.println("Unable to update seat count in DB.");

            }


        } else {
            System.out.println("Ticket is not eligible for cancellation.");
        }
    }

    // Can probably remove this method if it has no other functionality
    // can just call ticket method directly?
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
                }
                else {
                    refunded = true;
                }
                Ticket ticket = new Ticket(ticketID, showtimeID, seatID,  RUID, email, cost,  paymentID,  refunded);
                tickets.add(ticket);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tickets;
    }
    public boolean addTicket(Ticket ticket) {
        // takes ticket and saves it on DB
        // if successful, return TRUE
        String query = "INSERT INTO TICKET (RUID, showtimeID, SeatID, Cost, PaymentID, Email, TimePurchased, DatePurchased, Refunded) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ZoneId calgaryTimeZone = ZoneId.of("America/Edmonton"); // Calgary is in the "America/Edmonton" zone

// Get the current time in Calgary
        ZonedDateTime calgaryCurrentTime = ZonedDateTime.now(calgaryTimeZone);

// Extract the time (HH:MM:SS) from the ZonedDateTime
        LocalTime localTime = calgaryCurrentTime.toLocalTime();

// Format the time in "HH:MM:SS" format (compatible with MySQL TIME type)
        String formattedTime = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.toString();  // toString() gives YYYY-MM-DD

        try{
            PreparedStatement statement = jdbc.dbConnect.prepareStatement(query);
            statement.setInt(2, ticket.getShowtime().getShowtimeID());
            statement.setInt(3, ticket.getSeat().getSeatID());
            statement.setDouble(4, ticket.getTicketPrice());
            statement.setInt(5, ticket.getPayment().getPaymentID());
            statement.setString(6, ticket.getEmail());
            statement.setString(7, formattedTime);
            statement.setString(8, formattedDate);
            statement.setInt(9, 0); // not refunded
            if (ticket.getRU() != null){
                statement.setInt(1, ticket.getRU().getID());            }
            else{
                statement.setInt(1, -1);

            }
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e){
            System.out.println("Error adding ticket to DB: " + e.getMessage());
        }
        return false;
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

    public boolean updateShowtimeSeats(Showtime showtime) {
        // saves showtime AvailableRUSeats and AvailableOUSeats in DB
        // returns TRUE if successful
        String query = "UPDATE showtimes SET availableSeatsRU = ?, availableSeatsOU = ? WHERE showtimeID = ?"; // can we plz add available OU and RU seat columns in showtimes , thx
        try{
            PreparedStatement statement = jdbc.dbConnect.prepareStatement(query);
           // statement.setInt(1, showtime.getAvailableRUSeats());
           // statement.setInt(2, showtime.getAvailableOUSeats());
            statement.setInt(3, showtime.getShowtimeID());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        }catch(SQLException e) {
            System.out.println("Error updating showtime seats:  " + e.getMessage());
        }
        return false;
    }
}
