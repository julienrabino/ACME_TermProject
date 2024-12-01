public class Ticket {
    private int ticketID;
    private Showtime showtime;
    private int showtimeID;
    private RegisteredUser RU;
    private int seatID;
    private int paymentID;
    private int RUID;
    private Seat seat;
    private double ticketPrice;
    private Payment payment;
    private String email;
    private String paymentMethod;
    private boolean ticketStatus;
    private boolean refunded;
    private String datePurchased;
    private String timePurchased;
    private boolean isAnRUSeat; //boolean to check if the ticket saves a seat that was reserved for an RU

    @Override
    public String toString() {
        // Return a string in the format: "TicketID: [ticketID], Date Purchased: [datePurchased], Time Purchased: [timePurchased]"
        return "TicketID: " + ticketID + ", Date Purchased: " + datePurchased + ", Time Purchased: " + timePurchased;
    }


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
    public Ticket(int ticketID, int showtimeID, int seatID,  int RUID, String email,  double ticketPrice, int paymentID,  String datePurchased, String timePurchased, boolean refunded){
        this.ticketID = ticketID;
        this.showtimeID = showtimeID;
        this.RUID = RUID;
        this.email = email;
        this.seatID = seatID;
        this.ticketPrice = ticketPrice;
        this.paymentID = paymentID;
        this.datePurchased = datePurchased;
        this.timePurchased = timePurchased;
        //this.isAnRUSeat = isAnRUSeat;
        this.refunded = refunded;
        this.ticketStatus = true; // ticketStatus is TRUE when it is still a valid ticket
    }
    public Ticket(Showtime showtime, Seat seat,  RegisteredUser RU, String email,  double ticketPrice, Payment payment,  String datePurchased, String timePurchased, boolean isAnRUSeat, boolean refunded){
        this.showtime = showtime;
        this.RU = RU;
        this.email = email;
        this.seat = seat;
        this.ticketPrice = ticketPrice;
        this.payment = payment;
        this.isAnRUSeat = isAnRUSeat;
        this.datePurchased = datePurchased;
        this.timePurchased = timePurchased;
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
    public void setShowtimeID(int id) {
        this.showtimeID = id;
    }
    public int getShowtimeID() {
        return showtimeID;
    }
    public void setSeatID(int id) {
        this.seatID = id;
    }
    public int getSeatID() {
        return seatID;
    }

    public void setRefunded(boolean ref) {
        this.refunded = ref;
    }
    public boolean getRefunded() {
        return refunded;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public void setPaymentID(int id){
            this.paymentID = id;
    }
    public int getPaymentID(){
        return this.paymentID;
    }
    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
    public void setTicketStatus(boolean ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
    public void setRU(RegisteredUser RU) {
        this.RU = RU;
    }

    // getters
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public String getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(String datePurchased) {
        this.datePurchased = datePurchased;
    }

    // Getters and Setters for timePurchased
    public String getTimePurchased() {
        return timePurchased;
    }

    public void setTimePurchased(String timePurchased) {
        this.timePurchased = timePurchased;
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



}
