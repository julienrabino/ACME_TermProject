public class User {
    protected String Fname;
    protected String Lname;
    protected String email;
    protected boolean registered;
    protected Location theatreLocation;
    protected String paymentMethod;

    public User(){}
    
    public User(String Fname, String Lname,String email, Location theatreLocation){
        this.Fname = Fname;
        this.Lname = Lname;
        this.email = email;
        this.paymentMethod = null; //only to be set if the OU decides to purchase tickets
        this.theatreLocation = theatreLocation;
        registered = false;

    }
    //setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setFname(String name) {
        this.Fname = name;
    }
    public void setLname(String name) {
        this.Lname = name;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
    public void setTheatreLocation(Location theatreLocation) {
        this.theatreLocation = theatreLocation;
    }

    //getters
    public String getEmail() {
        return email;
    }
    public String getFname() {
        return Fname;
    }
    public String getLname() {
        return Lname;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public boolean getRegistered(){
        return registered;
    }
    public Location getTheatreLocation() {
        return theatreLocation;
    }
    
    
    // public void purchaseTicket(){}
}