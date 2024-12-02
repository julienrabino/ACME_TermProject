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


    private myJDBC jdbc;

    public TicketController(myJDBC myJDBC){
        this.jdbc = myJDBC;
    }










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
                Ticket ticket = new Ticket(ticketID, showtimeID, seatID, RUID, email, cost, paymentID, date, time, refunded);
                tickets.add(ticket);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tickets;
    }
    public ArrayList<Ticket> getTicketsFromEmail(String email) {
        ResultSet results;
        ArrayList<Ticket> tickets = new ArrayList<>();

        // SQL query to fetch tickets by email
        String query = "SELECT * FROM TICKET AS T WHERE T.Email = ?";

        try {
            // Use PreparedStatement to prevent SQL injection
            PreparedStatement myStmt = jdbc.dbConnect.prepareStatement(query);

            // Set the email parameter in the query
            myStmt.setString(1, email);

            // Execute the query
            results = myStmt.executeQuery();

            // Process the results
            while (results.next()) {
                int ticketID = results.getInt("TicketID");
                int showtimeID = results.getInt("ShowtimeID");
                int RUID = results.getInt("RUID");
                int paymentID = results.getInt("PaymentID");
                double cost = results.getDouble("Cost");
                String time = results.getString("TimePurchased");
                String date = results.getString("DatePurchased");
                int refund = results.getInt("Refunded");
                int seatID = results.getInt("SeatID");
                System.out.println("IN GET TICKETS FROM EMAIL, TICKET ID IS " + ticketID);

                // Determine if ticket is refunded
                boolean refunded = refund == 1;

                // Create a Ticket object and add to the list
                Ticket ticket = new Ticket(ticketID, showtimeID, seatID, RUID, email, cost, paymentID, date, time, refunded);
                tickets.add(ticket);
            }

            // Close statement
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
            statement.setString(7, ticket.getTimePurchased()); // TimePurchased
            statement.setString(8, ticket.getDatePurchased()); // DatePurchased
            statement.setInt(9, 0); // Refunded (0 for not refunded, 1 for refunded)

            // Execute the query
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            System.out.println("Error adding ticket to DB: " + e.getMessage());
        }

        return false; // Return false if there was an error
    }

    public Showtime getShowtimeFromID(int id) {
        String query = null;
        Showtime showtime = null;
        Movie movie = null;
        Location theatre = null;
        PreparedStatement statement = null;

        try {

            query = "SELECT * FROM SHOWTIME AS S WHERE S.ShowtimeID  = ?";
            statement = jdbc.dbConnect.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet results = statement.executeQuery();

            while (results.next()) {

                int movieID = results.getInt("MovieID");
                int theatreID = results.getInt("TheatreID");
                String showDate = results.getString("ShowDate");
                String showTime = results.getString("ShowTime");

                movie = getMovieFromID(movieID);
                theatre = getTheatreFromID(theatreID);
                showtime = new Showtime( id, movie, showDate, showTime, theatre);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtime;
    }

    public Movie getMovieFromID(int id) {
        String query = null;
        Movie movie = null;
        PreparedStatement statement = null;

        try {

            query = "SELECT * FROM MOVIE AS M WHERE M.MovieID  = ?";
            statement = jdbc.dbConnect.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet results = statement.executeQuery();

            while (results.next()) {

                String title = results.getString("Title");
                String genre = results.getString("Genre");
                String releaseDate = results.getString("ReleaseDate");

                movie = new Movie(id, title, genre, releaseDate);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    public Location getTheatreFromID(int id) {
        String query = null;
        Location location = null;
        PreparedStatement statement = null;

        try {

            query = "SELECT * FROM THEATRE AS T WHERE T.TheatreID  = ?";
            statement = jdbc.dbConnect.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet results = statement.executeQuery();

            while (results.next()) {

                String address = results.getString("Address");
                String name = results.getString("TheatreName");

                location = new Location(address, name,id);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return location;
    }

    public Seat getSeatFromID(int id) {
        String query = null;
        Seat seat = null;
        PreparedStatement statement = null;

        try {

            query = "SELECT * FROM SEAT AS S WHERE S.SeatID  = ?";
            statement = jdbc.dbConnect.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet results = statement.executeQuery();

            while (results.next()) {

                int showtimeID = results.getInt("Showtime");
                String seatRowStr = results.getString("SeatRow");
                int col = results.getInt("SeatColumn");
                boolean available = results.getBoolean("Available");
                boolean RUreserved = results.getBoolean("RUReserved");

                char row = (seatRowStr != null && seatRowStr.length() > 0) ? seatRowStr.charAt(0) : ' ';


                seat = new Seat(id,  row, col, showtimeID,RUreserved, available);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seat;
    }

    public void changeRefunded(Ticket ticket, boolean refunded) {
        int ticketID = ticket.getTicketID();
        System.out.println("IN CHANGE REFUNDED FUNC, TICKET ID IS " + ticketID);
        String query = null;
        PreparedStatement statement = null;

        try {
            // Update query that uses the 'available' parameter
            query = "UPDATE TICKET SET Refunded = ? WHERE TicketID = ?;";

            // Prepare the statement
            statement = jdbc.dbConnect.prepareStatement(query);
            int ref = 0;
            if (refunded){
                ref = 1;
            }

            // Set the parameters
            statement.setInt(1, ref);  // Set the 'Available' value
            statement.setInt(2, ticketID);  // Set the SeatID for which you want to update availability

            // Execute the update query
            int rowsAffected = statement.executeUpdate();  // Use executeUpdate for non-SELECT queries

            if (rowsAffected > 0) {
                System.out.println("Ticket refund updated successfully.");
            } else {
                System.out.println("No rows affected.");
            }

            // Close the statement
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<StoreCredit> getCreditsFromEmail(String Email) {

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



}
