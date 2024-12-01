import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieTheatreApp extends JFrame {
    private static myJDBC db;
    private static final CardLayout cardLayout = new CardLayout();
    public JPanel cards;  // main container to hold different panels (like cards)
    private InitialPanel initialPanel;
    private LoginPanel loginPanel;
    private SignupPanel signupPanel;
    private MovieListPanel movieListPanel;
    private GuestPanel guestPanel;
    private ConfirmPanel confirmPanel;

    private AdminPanel adminPanel;
    private PaymentRUPanel paymentRUPanel;
    private PaymentPanel paymentPanel;
    private RegisteredPanel registeredPanel;

    private UserDatabaseManager userDBM;

    private MovieTheatreController movieTC;
    private TicketController ticketC;
    private BillingSystem billingS;

    private int currentUser; // 0 for guest, 1 for RU, 2 for admin?
    // so we can check whos currently using our application and what to do from there yk... also if its RU store their info !
    // yes !
    private RegisteredUser RU;
    private Movie selectedMovie;
    private Showtime selectedShowtime;
    private Seat selectedSeat;
    private Location selectedLocation;

    private Payment selectedPayment;
    public void setCurrentUser (int user) {
        this.currentUser= user;
    }
    public int getCurrentUser () {
        return this.currentUser;
    }
    public void setRU (RegisteredUser user) {
        this.RU = user; // i think this is a shallow copy maybe but idk
    }
    public RegisteredUser getRU () {
        return this.RU;
    }

    public void setSelectedSeat(Seat seat) {
        selectedSeat = seat;
    }public Seat getSelectedSeat() {
        return selectedSeat;
    }

    public void setSelectedShowtime(Showtime showtime){
        this.selectedShowtime = showtime;
    }

    public Showtime getSelectedShowtime(){
        return this.selectedShowtime;
    }
    public Payment getSelectedPayment(){
        return this.selectedPayment;
    }
    public void setSelectedPayment(Payment payment){
        this.selectedPayment = payment;
    }


    public void setTheatre (Location loc) {
        this.selectedLocation = loc; // i think this is a shallow copy maybe but idk
    }
    public Location getTheatre () {
        return this.selectedLocation;
    }
    public ConfirmPanel getConfirmPanel () {
        return this.confirmPanel;
    }
    public PaymentRUPanel getPaymentRUPanel () {
        return this.paymentRUPanel;
    }
    public PaymentPanel getPaymentPanel () {
        return this.paymentPanel;
    }
    public LoginPanel getLoginPanel(){
        return loginPanel;
    }
    public UserDatabaseManager getUserDBM(){
        return userDBM;
    }
    public void setLoginPanel(LoginPanel loginPanel){
        this.loginPanel = loginPanel;
    }

    public MovieListPanel getMovieListPanel()
    {
        return movieListPanel;
    }
    public void setMovie (Movie movie) {
        this.selectedMovie = movie; // i think this is a shallow copy maybe but idk
    }
    public Movie getMovie () {
        return this.selectedMovie;
    }

    public MovieTheatreApp() {
        super("ACMEPLEX");
        db = new myJDBC();
        db.initializeConnection();
        movieTC = new MovieTheatreController(db);
        userDBM = new UserDatabaseManager(db);
        ticketC = new TicketController(db);
        billingS = new BillingSystem(db, this);
        // set up the main container with CardLayout
        cards = new JPanel(cardLayout);


        loginPanel = new LoginPanel(this, userDBM);
        movieListPanel = new MovieListPanel(this, movieTC);
        initialPanel = new InitialPanel(this);
        signupPanel = new SignupPanel(this, userDBM);
        guestPanel = new GuestPanel(this, movieTC);
        adminPanel = new AdminPanel(this, userDBM);
        confirmPanel = new ConfirmPanel(this, userDBM, movieTC);
        paymentPanel = new PaymentPanel(this, userDBM, ticketC, billingS);
        paymentRUPanel = new PaymentRUPanel(this, userDBM, ticketC);
        registeredPanel = new RegisteredPanel(this);


        cards.add(loginPanel, "Login");
        cards.add(movieListPanel, "Movies");
        cards.add(initialPanel, "Initial");
        cards.add(signupPanel, "Signup");
        cards.add(guestPanel, "Guest");
        cards.add(adminPanel, "Admin");
        cards.add(confirmPanel, "Confirm");
        cards.add(paymentPanel, "Payment");
        cards.add(paymentRUPanel, "PaymentRU");
        cards.add(registeredPanel, "Registered");


        this.add(cards);

        cardLayout.show(cards, "Initial");
        // Basic frame setup
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void switchToInitial() {
        // Switch to the Movie List panel after successful login
        cardLayout.show(cards, "Initial");
    }

    public void switchToMovieList() {
        // Switch to the Movie List panel after successful login
        cardLayout.show(cards, "Movies");
    }


    public void switchToLogin() {
        // Switch to the Movie List panel after successful login
        cardLayout.show(cards, "Login");
    }

    public void switchToRegister() {
        // Switch to the Movie List panel after successful login
        cardLayout.show(cards, "Signup");
    }

    public void switchToGuest() {
        // just browse and purchase movies i guess?
        cardLayout.show(cards, "Guest");
    }
    public void switchToAdmin() {
        // just browse and purchase movies i guess?
        cardLayout.show(cards, "Admin");
    }

    public void switchToConfirm() {
        // just browse and purchase movies i guess?
        cardLayout.show(cards, "Confirm");
    }
    public void switchToPayment(){
        cardLayout.show(cards, "Payment");
    }
    public void switchToPaymentRU(){
        cardLayout.show(cards, "PaymentRU");
    }
    public void switchToRegistered(){
        cardLayout.show(cards,"Registered");
    }

    public static void main(String[] args) {

        db = new myJDBC("jdbc:mysql://localhost:3306/MOVIE_THEATRE", "root", "123");
        db = new myJDBC();
        db.initializeConnection();


        EventQueue.invokeLater(() -> {
            new MovieTheatreApp().setVisible(true);
        });
    }


}







