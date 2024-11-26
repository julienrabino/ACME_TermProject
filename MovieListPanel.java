import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MovieListPanel extends JPanel{
    private ArrayList<Movie> movies;
    private ArrayList<Location> locations;
    private boolean showAll = true;
    private boolean search = false;
    JComboBox<Location> locationComboBox;


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
        locationComboBox = new JComboBox<>(locations.toArray(new Location[0]));

// Add an ActionListener to detect when the user selects a new location
        locationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Location selectedLocation = (Location) locationComboBox.getSelectedItem();
                // Use the selected location to update other components or details
                System.out.println("Selected Location: " + selectedLocation);
            }
        });

// Create the movie details label and align it to the left
        movieDetailsLabel = new JLabel("lalalalal");
        movieDetailsLabel.setHorizontalAlignment(SwingConstants.LEFT);

// Create the details panel with a BoxLayout to stack components vertically
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Stack components vertically

// Add movie details label
        detailsPanel.add(movieDetailsLabel);

// Add vertical space between the movie details label and the combo box
        detailsPanel.add(Box.createVerticalStrut(10));
        locationComboBox.setPreferredSize(new Dimension(200, 30));  // Preferred size
        locationComboBox.setMinimumSize(new Dimension(200, 30));     // Minimum size
        locationComboBox.setMaximumSize(new Dimension(200, 30));
// Add the location combo box directly below the movie details label
        detailsPanel.add(locationComboBox);

// Set the details panel to be hidden initially
        detailsPanel.setVisible(false);

// Set the preferred size for the panel
        detailsPanel.setPreferredSize(new Dimension(300, 500));

// Add the details panel to the EAST side of the layout
        this.add(detailsPanel, BorderLayout.EAST);

// Update movie list
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
                        locations.add(location);
                    }
                    // Update the JComboBox model to reflect the new locations
                    locationComboBox.setModel(new DefaultComboBoxModel<>(locations.toArray(new Location[0])));

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
