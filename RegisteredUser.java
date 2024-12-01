import java.util.Date;
import java.time.LocalDate;

public class RegisteredUser extends User{
    private int ID;
    private String address;
    private boolean paymentMethodSaved;
    private LocalDate expiryDate;
    // for their login purposes
    private String username;
    private String password;
    private String email;
    private LocalDate joinDate;

    public RegisteredUser(int id, String Fname, String Lname, String username, String password, String email, String Address, LocalDate expiryDate, LocalDate joinDate, boolean paymentMethodSaved ){

        super(Fname, Lname, email); // calls ctor of base class User
        this.ID = id;
        this.expiryDate = expiryDate;
        this.joinDate = joinDate;
        this.address = Address;
        this.username = username;
        this.password = password;
        this.email = email;
        this.paymentMethodSaved = paymentMethodSaved;

        if (paymentMethodSaved && paymentMethod != null){
            this.paymentMethod = paymentMethod;
        }
        // else if paymentMethod is not saved yet for this RU, they will be requested
        // to input their payment details when purchasing tickets/renewing Membership.
        // their payment details will be saved to the DB after and paymentMethodSaved will == true.

        registered = true;
    }
    public RegisteredUser(String Fname, String Lname, String username, String password, String email, String Address, LocalDate expiryDate, LocalDate joinDate, boolean paymentMethodSaved ){

        super(Fname, Lname, email); // calls ctor of base class User
        this.expiryDate = expiryDate;
        this.joinDate = joinDate;
        this.address = Address;
        this.username = username;
        this.password = password;
        this.email = email;
        this.paymentMethodSaved = paymentMethodSaved;

        if (paymentMethodSaved && paymentMethod != null){
            this.paymentMethod = paymentMethod;
        }
        // else if paymentMethod is not saved yet for this RU, they will be requested
        // to input their payment details when purchasing tickets/renewing Membership.
        // their payment details will be saved to the DB after and paymentMethodSaved will == true.

        registered = true;
    }
    public RegisteredUser(int id, String Fname, String Lname, String username, String password, String email, Location theatreLocation, LocalDate membershipExpiry, String Address, boolean paymentMethodSaved, String paymentMethod ){

        super(Fname, Lname, email, theatreLocation); // calls ctor of base class User
        this.expiryDate = membershipExpiry;
        this.address = Address;
        this.username = username;
        this.password = password;

        if (paymentMethodSaved && paymentMethod != null){
            this.paymentMethod = paymentMethod;
        }
        // else if paymentMethod is not saved yet for this RU, they will be requested
        // to input their payment details when purchasing tickets/renewing Membership.
        // their payment details will be saved to the DB after and paymentMethodSaved will == true.

        registered = true;
    }

    //getters
    public int getID() {
        return ID;
    }
    public String getAddress() {
        return address;
    }
    public LocalDate getMembershipExpiry() {
        return expiryDate;
    }
    public LocalDate getJoinDate() {
        return joinDate;
    }
    public boolean getPaymentMethodSaved(){
        return paymentMethodSaved;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    //setters
    public void setID(int iD) {
        ID = iD;
    }public void setAddress(String address) {
        this.address = address;
    }public void setPaymentMethodSaved(boolean paymentMethodSaved) {
        this.paymentMethodSaved = paymentMethodSaved;
    }public void setMembershipExpiry(LocalDate membershipExpiry) {
        this.expiryDate = membershipExpiry;
    }
    public void setUsername(String usr) {
        this.username = usr;
    }
    public void setPassword(String pw) {
        this.password = pw;
    }


}
