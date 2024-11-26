public class Showtime {
    private int showtimeID;
    private Movie movie;
    private String time;
    private String date;
    private int availableRUSeats;
    private int availableOUSeats;
    private int totalRUSeats;
    private int totalOUSeats;
    private Location location;




    public Showtime(int showtimeID, Movie movie, String date, String time, Location location,  int totalRUSeats, int totalOUSeats, int availableRUSeats, int availableOUSeats){
        this.showtimeID = showtimeID;
        this.movie = movie;
        this.time = time;
        this.date = date;
        this.availableRUSeats = availableRUSeats;
        this.availableOUSeats = availableOUSeats;

        this.totalOUSeats = totalOUSeats;
        this.totalRUSeats = totalRUSeats;

        this.location = location;
    }



    @Override
    public String toString(){
        return time;
    }
    //getters
    public String getDate(){ return date;}
    public int getShowtimeID() {
        return showtimeID;
    }
    public Movie getMovie() {
        return movie;
    }
    public String getTime() {
        return time;
    }
    public int getAvailableRUSeats() {
        return availableRUSeats;
    }
    public int getAvailableOUSeats() {
        return availableOUSeats;
    }
    public Location getLocation() {
        return location;
    }
    public int getTotalRUSeats() {
        return totalRUSeats;
    }
    public int getTotalOUSeats() {
        return totalOUSeats;
    }

    //setters
    public void setAvailableOUSeats(int availableOUSeats) {
        this.availableOUSeats = availableOUSeats;
    }
    public void setAvailableRUSeats(int availableRUSeats) {
        this.availableRUSeats = availableRUSeats;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public void setShowtimeID(int showtimeID) {
        this.showtimeID = showtimeID;
    }
    public void setTime(String time) {
        this.time = time;
    }

    /*
        updateSeats() --> returns true if SUCCESSFUL, returns FALSE otherwise
     */
    public boolean updateSeats(boolean isAnRUSeat, int numSeatsBooked, boolean ticketPurchase){
        // this for now, but need to implement
        // RUs can reserve both RU and OU seats, but since we need to track the numOUSeats and numRUSeats
        // when cancelling/purchasing tickets, isAnRUSeat is a Ticket attribute to check if the ticket
        // is a booking for one of the 10% RU reserved seats. User type doesn't matter at this step.

        boolean success = false;
        if (numSeatsBooked <= 0) {
            System.out.println("Invalid number of seats.");
            return false;
        }

        if (ticketPurchase){
            if (isAnRUSeat && (this.availableRUSeats - numSeatsBooked) >= 0){
                this.availableRUSeats -= numSeatsBooked;
                success = true;
            } else if (!isAnRUSeat && (this.availableOUSeats - numSeatsBooked >= 0) ){
                this.availableOUSeats -= numSeatsBooked;
                success = true;
            }

        // else if ticket cancellation
        } else{
            if (isAnRUSeat && (this.availableRUSeats + numSeatsBooked <= this.totalRUSeats)){
                this.availableRUSeats += numSeatsBooked;
                success = true;
            } else if (!isAnRUSeat && (this.availableOUSeats + numSeatsBooked <= this.totalOUSeats)){
                this.availableOUSeats += numSeatsBooked;
                success = true;
            }

        }

        return success;


    }
}
