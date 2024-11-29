import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieTheatreApp extends JFrame {
    private static myJDBC db;
    private static final CardLayout cardLayout = new CardLayout();
    private JPanel cards;  // main container to hold different panels (like cards)
    private InitialPanel initialPanel;
    private LoginPanel loginPanel;
    private SignupPanel signupPanel;
    private MovieListPanel movieListPanel;
    private GuestPanel guestPanel;
    private ConfirmPanel confirmPanel;

    private AdminPanel adminPanel;

    private UserDatabaseManager userDBM;

    private MovieTheatreController movieTC;

    private int currentUser; // 0 for guest, 1 for RU, 2 for admin?
    // so we can check whos currently using our application and what to do from there yk... also if its RU store their info !
    private RegisteredUser RU;

    private Movie selectedMovie;
    private Showtime selectedShowtime;
    private Seat selectedSeat;
    private Location selectedLocation;
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

    public void setMovie (Movie movie) {
        this.selectedMovie = movie; // i think this is a shallow copy maybe but idk
    }
    public Movie getMovie () {
        return this.selectedMovie;
    }
    public void setShowtime (Showtime showtime) {
        this.selectedShowtime = showtime; // i think this is a shallow copy maybe but idk
    }
    public Showtime getShowtime () {
        return this.selectedShowtime;
    }
    public void setSeat (Seat seat) {
        this.selectedSeat = seat; // i think this is a shallow copy maybe but idk
    }
    public Seat getSeat () {
        return this.selectedSeat;
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

    public MovieTheatreApp() {
        super("ACMEPLEX");
        db = new myJDBC();
        db.initializeConnection();
        movieTC = new MovieTheatreController(db);
        userDBM = new UserDatabaseManager(db);

        // set up the main container with CardLayout
        cards = new JPanel(cardLayout);


        loginPanel = new LoginPanel(this, userDBM);
        movieListPanel = new MovieListPanel(this, movieTC);
        initialPanel = new InitialPanel(this);
        signupPanel = new SignupPanel(this, userDBM);
        guestPanel = new GuestPanel(this, movieTC);
        adminPanel = new AdminPanel(this, userDBM);
        confirmPanel = new ConfirmPanel(this, userDBM, movieTC);

        cards.add(loginPanel, "Login");
        cards.add(movieListPanel, "Movies");
        cards.add(initialPanel, "Initial");
        cards.add(signupPanel, "Signup");
        cards.add(guestPanel, "Guest");
        cards.add(adminPanel, "Admin");
        cards.add(confirmPanel, "Confirm");

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

    public void switchToConfrim() {
        // just browse and purchase movies i guess?
        cardLayout.show(cards, "Confirm");
    }

    public static void main(String[] args) {
//        String url = "";
//        String user = "";
//        String pw = "";
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter your database url (e.g. jdbc:mysql://localhost:3306/MOVIE_THEATRE): ");
//        url = scanner.nextLine();
//        System.out.println("Enter your database username (e.g. root): ");
//        user = scanner.nextLine();
//        System.out.println("Enter your database password: ");
//        pw = scanner.nextLine();
//        //myJDBC db = new myJDBC(url, user, pw);
        db = new myJDBC("jdbc:mysql://localhost:3306/MOVIE_THEATRE", "root", "123");
        db = new myJDBC();
        db.initializeConnection();
//        MovieTheatreController movieTC = new MovieTheatreController(db);
//        displayMovies(movieTC, scanner);
//        searchMovie(movieTC, scanner);
//        displayShowtimes(movieTC, scanner);
//

        EventQueue.invokeLater(() -> {
            new MovieTheatreApp().setVisible(true);
        });
    }

}







