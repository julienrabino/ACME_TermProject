public class Ticket {
    private int ticketID;
    private Showtime showtime;
    private User user;
    private int seat;
    private double ticketPrice;
    private String paymentMethod;
    private boolean ticketStatus;
    private boolean isAnRUSeat; //boolean to check if the ticket saves a seat that was reserved for an RU


    public Ticket(int ticketID, Showtime showtime, User user, int seat, double ticketPrice, String paymentMethod, boolean isAnRUSeat ){
        this.ticketID = ticketID;
        this.showtime = showtime;
        this.user = user;
        this.seat = seat;
        this.ticketPrice = ticketPrice;
        this.paymentMethod = paymentMethod;
        this.isAnRUSeat = isAnRUSeat;
        this.ticketStatus = true; // ticketStatus is TRUE when it is still a valid ticket
    }

    //setters

    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }public void setSeat(int seat) {
        this.seat = seat;
    }public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }public void setTicketStatus(boolean ticketStatus) {
        this.ticketStatus = ticketStatus;
    }public void setUser(User user) {
        this.user = user;
    }

    // getters
    public String getPaymentMethod() {
        return paymentMethod;
    }public int getSeat() {
        return seat;
    }public Showtime getShowtime() {
        return showtime;
    }public int getTicketID() {
        return ticketID;
    }public double getTicketPrice() {
        return ticketPrice;
    }public User getUser() {
        return user;
    }public boolean getTicketStatus() {
        return ticketStatus;
    }public boolean getIsAnRUSeat(){
        return isAnRUSeat;
    }

    // more functions
    public void cancelTicket(){
        this.ticketStatus = false;
        Showtime showtime = this.showtime;
       // showtime.updateSeats(this.isAnRUSeat, 1,false);

    }
    public void sendTicketReceipt(Ticket ticket){
        Showtime showtime = this.showtime;
        Movie movie = showtime.getMovie();
        System.out.println("The following ticket receipt was sent to: "+ ticket.user.getEmail());
        System.out.println("Movie: "+ movie.getTitle());
        System.out.println("Genre: "+ movie.getGenre());
        System.out.println("Time: "+ showtime.getTime());
        System.out.println("Ticket ID: "+ this.ticketID);
        System.out.println("Ticket price: "+ this.ticketPrice);
        System.out.println("Payment Method: "+ this.paymentMethod);
    }

}
