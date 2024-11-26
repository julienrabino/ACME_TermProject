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
    private JPanel cards;  // main container to hold different panels (like cards)
    private InitialPanel initialPanel;
    private LoginPanel loginPanel;
    private SignupPanel signupPanel;
    private MovieListPanel movieListPanel;
    private GuestPanel guestPanel;


    private UserDatabaseManager userDBM;

    private MovieTheatreController movieTC;

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

        cards.add(loginPanel, "Login");
        cards.add(movieListPanel, "Movies");
        cards.add(initialPanel, "Initial");
        cards.add(signupPanel, "Signup");
        cards.add(guestPanel, "Guest");

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







