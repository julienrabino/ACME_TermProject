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

    public int pickedLocationID;
    public boolean locationSpecified;

    private MovieTheatreController movieTC;

    public MovieTheatreApp() {
        super("ACMEPLEX");
        db = new myJDBC();
        db.initializeConnection();
        movieTC = new MovieTheatreController(db);
        pickedLocationID = -1;
        locationSpecified = false;

        // set up the main container with CardLayout
        cards = new JPanel(cardLayout);


        loginPanel = new LoginPanel(this, db);
        movieListPanel = new MovieListPanel(this, movieTC);
        initialPanel = new InitialPanel(this);
        signupPanel = new SignupPanel(this);
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

    public void switchToGuest(){
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


    public static void searchMovie(MovieTheatreController movieTC, Scanner scanner) {
        String search = "";
        System.out.println("Search Movie: ");
        search = scanner.nextLine().trim();
        movieTC.fetchMovies(search);

        ArrayList<Movie> results = movieTC.fetchMovies(search);
        if (results.isEmpty()) {
            System.out.println("No movies found.");
            return;
        }

        System.out.println("Movies that matched your search:");
        for (Movie movie : results) {
            System.out.println("Movie ID: " + movie.getMovieID());
            System.out.println("Title: " + movie.getTitle());
            System.out.println("Genre: " + movie.getGenre());
        }



    }
    public static void displayMovies(MovieTheatreController movieTC, Scanner scanner) {

        // see all movies across ALL locations
        // add code to ask user if they want to see movies based on location or not
        ArrayList<Movie> allMovies = movieTC.fetchMovies(-1);
        if (allMovies.isEmpty()) {
            System.out.println("No movies available.");
            return;
        }

        System.out.println("All movies:");
        for (Movie movie : allMovies) {
            System.out.println("Movie ID: " + movie.getMovieID());
            System.out.println("Title: " + movie.getTitle());
            System.out.println("Genre: " + movie.getGenre());
        }


    }

    public static void displayShowtimes(MovieTheatreController movieTC, Scanner scanner){
        String search = "";
        System.out.println("Which Movie would you like to see showtimes for? ");

        search = scanner.nextLine().trim();
       // movieTC.displayShowtimes();

    }
 }

 class InitialPanel extends JPanel {
    private MovieTheatreApp app;

    public InitialPanel(MovieTheatreApp app) {
        this.app = app;


        JButton loginButton = new JButton("login");
        JButton registerButton = new JButton("sign up");
        JButton guestButton = new JButton("continue as guest");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.switchToLogin();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.switchToRegister();
            }
        });
        guestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.switchToGuest();
            }
        });

        this.setLayout(new FlowLayout());
        this.add(loginButton);
        this.add(registerButton);
        this.add(guestButton);
    }
}
class LoginPanel extends JPanel {
    private int width = 100;
    private int height = 30;
    private JTextField usrInput;
    private JPasswordField pwInput;

    private MovieTheatreApp app;

    public LoginPanel(MovieTheatreApp app, myJDBC db) {
        this.app = app;

        JLabel usrLabel = new JLabel("Username:");
        JLabel pwLabel = new JLabel("Password:");

        usrInput = new JTextField(15);
        pwInput = new JPasswordField(15);
        usrInput.setPreferredSize(new Dimension(width, height));
        pwInput.setPreferredSize(new Dimension(width, height));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usrInput.getText();
                String password = new String(pwInput.getPassword());

                // Need to change/add to this stuff still
                if ("admin".equals(username) && "password".equals(password)) {
                    JOptionPane.showMessageDialog(app, "Logged in successfully!");
                    app.switchToMovieList();  // switch to the movie list panel
                } else {
                    JOptionPane.showMessageDialog(app, "Invalid username or password.");
                }
            }
        });

        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        JLabel title = new JLabel("Sign In");
        headerPanel.add(title);

        this.add(headerPanel, BorderLayout.NORTH);

        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        submitPanel.add(submitButton);
        this.add(submitPanel, BorderLayout.PAGE_END);

        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);


        int row = 0;
        addComponent(clientPanel, usrLabel, 0, row, gbc);
        addComponent(clientPanel, usrInput, 1, row++, gbc);

        addComponent(clientPanel, pwLabel, 0, row, gbc);
        addComponent(clientPanel, pwInput, 1, row++, gbc);

        this.add(clientPanel, BorderLayout.CENTER);

    }
    private void addComponent(JPanel panel, Component component, int gridx, int gridy, GridBagConstraints gbc) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        panel.add(component, gbc);
    }

}
class SignupPanel extends JPanel {
    private int width = 100;
    private int height = 30;
    private RegisteredUser RU;
    private JTextField usrInput;
    private JPasswordField pwInput;
    private JPasswordField pwConfirmInput;
    private JTextField fnameInput;
    private JTextField lnameInput;
    private JTextField emailInput;
    private JTextField addressInput;

    private MovieTheatreApp app;

    public SignupPanel(MovieTheatreApp app) {
        this.app = app;

        JLabel usrLabel = new JLabel("Username:");
        JLabel pwLabel = new JLabel("Password:");
        JLabel pwConfirmLabel = new JLabel("Confirm Password:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel fnameLabel = new JLabel("First Name:");
        JLabel lnameLabel = new JLabel("Last Name:");
        JLabel addressLabel = new JLabel("Address:");

        usrInput = new JTextField(15);
        pwInput = new JPasswordField(15);
        pwConfirmInput = new JPasswordField(15);
        fnameInput = new JTextField(15);
        lnameInput = new JTextField(15);
        emailInput = new JTextField(30);
        addressInput = new JTextField(50);
        // set fixed size text fields
        usrInput.setPreferredSize(new Dimension(width, height));
        pwInput.setPreferredSize(new Dimension(width, height));
        pwConfirmInput.setPreferredSize(new Dimension(width, height));
        fnameInput.setPreferredSize(new Dimension(width, height));
        lnameInput.setPreferredSize(new Dimension(width, height));
        emailInput.setPreferredSize(new Dimension(width, height));
        addressInput.setPreferredSize(new Dimension(width, height));

//        JPanel clientPanel = new JPanel();
//        clientPanel.setLayout(new FlowLayout());
//
//        JPanel submitPanel = new JPanel();
//        submitPanel.setLayout(new FlowLayout());

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int safe = 1;
                String username = usrInput.getText();
                String pw = new String(pwInput.getPassword());
                String pwConfirm = new String(pwConfirmInput.getPassword());
                String fname = new String(fnameInput.getText());
                String lname = new String(lnameInput.getText());
                String email = new String(emailInput.getText());
                String address = new String(addressInput.getText());

                if (!pw.equals(pwConfirm)) {
                    safe = 0;
                    JOptionPane.showMessageDialog(app, "Passwords do not match.");
                }
                // DO A CHECK TO MAKE SURE THE USERNAME IS NOT ALREADY IN DB
                // AFTER CHECK MAKE THE RU AND SET THEIR INFO

                // ^^ can probably use UserDatabaseManager

                if (safe == 1) {
                    // set RU stuff maybe and add them to db?
                    // switch to a diff panel
                    app.switchToMovieList();
                }

            }
        });

        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        JLabel title = new JLabel("Please Fill Out All Fields: ");
        headerPanel.add(title);
        this.add(headerPanel, BorderLayout.NORTH);

        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);


        int row = 0;
        addComponent(clientPanel, fnameLabel, 0, row, gbc);
        addComponent(clientPanel, fnameInput, 1, row++, gbc);

        addComponent(clientPanel, lnameLabel, 0, row, gbc);
        addComponent(clientPanel, lnameInput, 1, row++, gbc);

        addComponent(clientPanel, emailLabel, 0, row, gbc);
        addComponent(clientPanel, emailInput, 1, row++, gbc);

        addComponent(clientPanel, addressLabel, 0, row, gbc);
        addComponent(clientPanel, addressInput, 1, row++, gbc);

        addComponent(clientPanel, usrLabel, 0, row, gbc);
        addComponent(clientPanel, usrInput, 1, row++, gbc);

        addComponent(clientPanel, pwLabel, 0, row, gbc);
        addComponent(clientPanel, pwInput, 1, row++, gbc);

        addComponent(clientPanel, pwConfirmLabel, 0, row, gbc);
        addComponent(clientPanel, pwConfirmInput, 1, row++, gbc);


        this.add(clientPanel, BorderLayout.CENTER);

        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        submitPanel.add(submitButton);

        this.add(submitPanel, BorderLayout.PAGE_END);
    }
    // Utility method to add components to the GridBagLayout
    private void addComponent(JPanel panel, Component component, int gridx, int gridy, GridBagConstraints gbc) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        panel.add(component, gbc);
    }

}

class MovieListPanel extends JPanel {
    private ArrayList<Movie> movies;
    private ArrayList<String> locations;
    private boolean showAll = true;
    private boolean search = false;
    JComboBox<String> locationComboBox;


    private String searchMovie;
    private JTextField searchInput;
    private JList<Movie> movieList;
    private DefaultListModel<Movie> listModel;
    private JButton showAllButton;
    private JLabel movieDetailsLabel;

    private JPanel detailsPanel;


    public MovieListPanel(MovieTheatreApp app, MovieTheatreController movieTC) {
        JLabel locationFilter = new JLabel("Choose a location:");


        // layout for the main panel
        this.setLayout(new BorderLayout());


        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));


        showAllButton = new JButton("Show All");
        showAllButton.setVisible(false);
        showAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                search = false;
                showAll = true;
                searchInput.setText(""); // so whenever u press show all, ur search bar clears!!
                updateMovieList(movieTC, app);
                showAllButton.setVisible(false);
            }
        });


        JLabel searchLabel = new JLabel("Search a Movie:");
        searchInput = new JTextField(30);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchMovie = searchInput.getText();
                search = true;
                showAll = false;
                updateMovieList(movieTC, app);
                showAllButton.setVisible(true);
            }
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchInput);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        this.add(searchPanel, BorderLayout.NORTH);

        //create list model for the JList
        listModel = new DefaultListModel<>();
        movieList = new JList<>(listModel);  // initialize the JList with the empty list model

        // scroll pane for the JList
        JScrollPane movieListScrollPane = new JScrollPane(movieList);
        movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.add(movieListScrollPane, BorderLayout.CENTER);

        // STUFF FOR DETAILS PANEL !!!!
        // Create an ArrayList of locations
        locations = new ArrayList<>();

// Create the JComboBox and populate it with the locations from the ArrayList
        locationComboBox = new JComboBox<>(locations.toArray(new String[0]));

// Add an ActionListener to detect when the user selects a new location
        locationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLocation = (String) locationComboBox.getSelectedItem();
                // Use the selected location to update other components or details
                System.out.println("Selected Location: " + selectedLocation);
            }
        });

        movieDetailsLabel = new JLabel("lalalalal");

        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());
        detailsPanel.add(movieDetailsLabel);

        detailsPanel.add(locationComboBox, BorderLayout.SOUTH);

// Initially hide the details panel
        detailsPanel.setVisible(false);  // Hide initially

// Fixed size for the panel
        detailsPanel.setPreferredSize(new Dimension(300, 500));

        this.add(detailsPanel, BorderLayout.EAST);



        updateMovieList(movieTC, app);
    }


    private void updateMovieList(MovieTheatreController movieTC, MovieTheatreApp app) {
        listModel.clear();  // clear existing list items

        if (showAll) {
            movies = movieTC.fetchMovies(-1);
        }
        else if (search){
            movies = movieTC.fetchMovies(searchMovie);
        }

        for (Movie movie : movies) {
            System.out.println(movie.getMovieID());
            listModel.addElement(movie); // Add the entire movie object to the list model
        }

        movieList.addListSelectionListener(e -> {
            // Only update when the selection is finalized (i.e., no longer adjusting)
            if (!e.getValueIsAdjusting()) {
                // Get the selected movie from the list
                Movie selectedMovie = movieList.getSelectedValue();
                System.out.println(selectedMovie.getMovieID());

                if (selectedMovie != null) {
                    // Movie is selected, show the details panel
                    detailsPanel.setVisible(true);  // Show the panel

                    // Format movie details in HTML
                    String movieDetails = "<html><b>Title:</b> " + selectedMovie.getTitle() +
                            "<br><b>Genre:</b> " + selectedMovie.getGenre() + "</html>";

                    // Update the panel with the movie details
                    movieDetailsLabel.setText(movieDetails);
                    // use query function to return all locations the movie is available at !!!
                    ArrayList<Location> loc = new ArrayList<>();
                    loc = movieTC.getMovieLocations(selectedMovie);

                    // Debug: Check if locations are returned
                    // um maybe put a label where it says no current locations available ? idk,
                    // or just only show movies that can be watched lol
                    if (loc != null && !loc.isEmpty()) {
                        System.out.println("Locations found for movie: " + selectedMovie.getTitle());
                    } else {
                        System.out.println("No locations found for movie: " + selectedMovie.getTitle());
                    }

                    locations.clear(); //clear the old locations !!!
                    for (Location location : loc) {
                       locations.add(location.getLocationName());
                    }
                    // Update the JComboBox model to reflect the new locations
                    locationComboBox.setModel(new DefaultComboBoxModel<>(locations.toArray(new String[0])));

                    // Optionally, set a default selection (e.g., first location)
                    if (!locations.isEmpty()) {
                        locationComboBox.setSelectedIndex(0); // Select the first location
                    }

                } else {
                    // No movie is selected, hide the details panel
                    detailsPanel.setVisible(false);  // Hide the panel
                }
            }
        });

    }
}


class LocationListPanel extends JPanel{
    public LocationListPanel(MovieTheatreApp app, MovieTheatreController movieTC ){
        // supposed to be an interface to show user what the locations are
        // should set the MovieTheatreApp attribute "LocationID" to whatever the user picks
    }
}

class GuestPanel extends JPanel{
    public GuestPanel(MovieTheatreApp app, MovieTheatreController movieTC){

        JButton browseMovies = new JButton("Browse Movies");
        browseMovies.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.switchToMovieList();
            }
        });

        this.setLayout(new FlowLayout());
        this.add(browseMovies);


    }

}





