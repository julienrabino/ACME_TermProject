public class Ticket {
    private int ticketID;
    private Showtime showtime;
    private RegisteredUser RU;
    private Seat seat;
    private double ticketPrice;
    private Payment payment;
    private String email;
    private String paymentMethod;
    private boolean ticketStatus;
    private boolean refunded;
    private boolean isAnRUSeat; //boolean to check if the ticket saves a seat that was reserved for an RU


    public Ticket(int ticketID, Showtime showtime, RegisteredUser RU, Seat seat, double ticketPrice, String paymentMethod, boolean isAnRUSeat ){
        this.ticketID = ticketID;
        this.showtime = showtime;
        this.RU = RU;
        this.seat = seat;
        this.ticketPrice = ticketPrice;
        this.paymentMethod = paymentMethod;
        this.isAnRUSeat = isAnRUSeat;
        this.ticketStatus = true; // ticketStatus is TRUE when it is still a valid ticket
    }

    public Ticket(int ticketID, Showtime showtime, Seat seat,  RegisteredUser RU,String email,  double ticketPrice, Payment payment, boolean isAnRUSeat, boolean refunded){
        this.ticketID = ticketID;
        this.showtime = showtime;
        this.RU = RU;
        this.email = email;
        this.seat = seat;
        this.ticketPrice = ticketPrice;
        this.payment = payment;
        this.isAnRUSeat = isAnRUSeat;
        this.refunded = refunded;
        this.ticketStatus = true; // ticketStatus is TRUE when it is still a valid ticket
    }
    public Ticket(Showtime showtime, Seat seat,  RegisteredUser RU, String email,  double ticketPrice, Payment payment, boolean isAnRUSeat, boolean refunded){
        this.showtime = showtime;
        this.RU = RU;
        this.email = email;
        this.seat = seat;
        this.ticketPrice = ticketPrice;
        this.payment = payment;
        this.isAnRUSeat = isAnRUSeat;
        this.refunded = refunded;
        this.ticketStatus = true; // ticketStatus is TRUE when it is still a valid ticket
    }

    //setters

    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public void setSeat(Seat seat) {
        this.seat = seat;
    }
    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    public Payment getPayment() {
        return payment;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }public void setTicketStatus(boolean ticketStatus) {
        this.ticketStatus = ticketStatus;
    }public void setRU(RegisteredUser RU) {
        this.RU = RU;
    }

    // getters
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public Seat getSeat() {
        return seat;
    }public Showtime getShowtime() {
        return showtime;
    }public int getTicketID() {
        return ticketID;
    }public double getTicketPrice() {
        return ticketPrice;
    }
    public RegisteredUser getRU() {
        return RU;
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
        System.out.println("The following ticket receipt was sent to: "+ ticket.getEmail());
        System.out.println("Movie: "+ movie.getTitle());
        System.out.println("Genre: "+ movie.getGenre());
        System.out.println("Time: "+ showtime.getDate() + " @ " + showtime.getTime());
        System.out.println("Ticket ID: "+ this.ticketID);
        System.out.println("Ticket price: "+ this.ticketPrice);
        System.out.println("Payment Method: "+ this.paymentMethod); // maybe return last 4 digits of card number?
    }

}
