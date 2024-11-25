// can probably delete

public class TicketDatabaseManager {
    TicketDatabaseManager(){}

    public boolean addTicketToDB(Ticket ticket) {

        // takes ticket and saves it on DB
        // if successful, return TRUE

        return false;
    }

    public boolean updateTicketStatus(Ticket ticket) {

        // uses the ticketStatus attribute of ticket to update the ticketStatus on DB
        // if successful, return TRUE

        return false;
    }

    public int produceTicketID() {
        // takes max ticket ID from the ticket table from DB and increments it by 1
        // returns this int
        // wait i think this could also be done in the databse but idk which way we wanna implememnt this
        // -->  we can but i was jus thinking for addTicketToDB the ticket object should alr have an id before insertion ahhh
        return 0;
    }

    public boolean fetchCancellationEligibility(Ticket ticket) {
        // queries DB if the ticketID associated with the ticket is still eligible for cancellation
        // returns this value
        return false;
    }

    public Ticket fetchTicket(int ticketID) {
        // queries DB for existing ticket info, based on ticketID
        // using this info, make and return a Ticket object

        return null;
    }

    public boolean updateShowtimeSeats(Showtime showtime) {
        // saves showtime AvailableRUSeats and AvailableOUSeats in DB
        // returns TRUE if successful
        return false;
    }

}
