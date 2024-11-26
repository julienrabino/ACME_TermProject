import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MovieListPanel extends JPanel {
    private ArrayList<Movie> movies;
    private ArrayList<Location> locations;
    private boolean showAll = true;
    private boolean search = false;
    JComboBox<Location> locationComboBox;
    private Movie lastSelectedMovie = null;

    private String searchMovie;
    private JTextField searchInput;
    private JList<Movie> movieList;
    private DefaultListModel<Movie> listModel;
    private JButton showAllButton;
    private JLabel movieDetailsLabel;
    private JPanel detailsPanel;
    private JPanel showtimesPanel;
    private MovieTheatreController movieTC;

    public MovieListPanel(MovieTheatreApp app, MovieTheatreController movieTC) {

        this.movieTC = movieTC;
        JButton backButton = new JButton("Back");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.switchToGuest();
            }
        });
        // layout for the main panel
        this.setLayout(new BorderLayout());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(backButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
        backButton.setVisible(true);


        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        showAllButton = new JButton("Show All");
        showAllButton.setVisible(false);
        showAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                search = false;
                showAll = true;
                searchInput.setText(""); // so whenever you press show all, your search bar clears
                updateMovieList(app);
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
                updateMovieList(app);
                showAllButton.setVisible(true);
            }
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchInput);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        this.add(searchPanel, BorderLayout.NORTH);

        // Create list model for the JList
        listModel = new DefaultListModel<>();
        movieList = new JList<>(listModel);  // initialize the JList with the empty list model

        // scroll pane for the JList
        JScrollPane movieListScrollPane = new JScrollPane(movieList);
        movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.add(movieListScrollPane, BorderLayout.CENTER);
        showtimesPanel = new JPanel();
        // Stuff for the details panel
        locations = new ArrayList<>();
        locationComboBox = new JComboBox<>(locations.toArray(new Location[0]));

        locationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Location selectedLocation = (Location) locationComboBox.getSelectedItem();
                System.out.println("Selected Location: " + selectedLocation);
                showtimesPanel.removeAll();
                detailsPanel.add(showtimesPanel);
                displayShowtimes(selectedLocation, lastSelectedMovie);
            }
        });

        movieDetailsLabel = new JLabel("lalalalal");
        movieDetailsLabel.setHorizontalAlignment(SwingConstants.LEFT);

        detailsPanel = new JPanel();
        detailsPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Stack components vertically
        detailsPanel.add(movieDetailsLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        locationComboBox.setPreferredSize(new Dimension(200, 30));  // Preferred size
        locationComboBox.setMinimumSize(new Dimension(200, 30));     // Minimum size
        locationComboBox.setMaximumSize(new Dimension(200, 30));
        detailsPanel.add(locationComboBox);
        detailsPanel.setVisible(false);

        detailsPanel.setPreferredSize(new Dimension(300, 500));
        this.add(detailsPanel, BorderLayout.EAST);

        // Initialize the movie list
        updateMovieList( app);
    }

    private void updateMovieList(MovieTheatreApp app) {
        listModel.clear();  // Clear existing list items

        // Fetch movies based on search state
        if (showAll) {
            movies = movieTC.fetchMovies(-1);
        } else if (search) {
            movies = movieTC.fetchMovies(searchMovie);
        }

        if (movies == null) {
            listModel.clear();
            return;
        }

        for (Movie movie : movies) {
            if (movie != null) {
                listModel.addElement(movie); // Add the entire movie object to the list model
            }
        }

        addMovieSelectionListener(movieTC);
    }

    // Separate method to handle the movie selection event
    private void addMovieSelectionListener(MovieTheatreController movieTC) {
        movieList.addListSelectionListener(e -> {
            // Only update when the selection is finalized (i.e., no longer adjusting)
            if (!e.getValueIsAdjusting()) {
                // Get the selected movie from the list
                Movie selectedMovie = movieList.getSelectedValue();

                if (selectedMovie != null) {
                    // Prevent redundant updates if the selected movie is the same as the last selected one
                    if (lastSelectedMovie != null && lastSelectedMovie.equals(selectedMovie)) {
                        return;
                    }

                    lastSelectedMovie = selectedMovie;
                    detailsPanel.setVisible(true);

                    // Format movie details in HTML
                    String movieDetails = "<html><b>Title:</b> " + selectedMovie.getTitle() +
                            "<br><b>Genre:</b> " + selectedMovie.getGenre() + "</html>";

                    // Update the panel with the movie details
                    movieDetailsLabel.setText(movieDetails);

                    // Fetch locations for the selected movie
                    ArrayList<Location> loc = movieTC.getMovieLocations(selectedMovie);

                    // Debug: Check if locations are returned
                    if (loc != null && !loc.isEmpty()) {
                        System.out.println("Locations found for movie: " + selectedMovie.getTitle());
                    } else {
                        System.out.println("No locations found for movie: " + selectedMovie.getTitle());
                    }

                    locations.clear(); // Clear old locations
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
    public void displayShowtimes(Location location, Movie movie) {
        showtimesPanel.removeAll();  // Clear old components
        ArrayList<Showtime> showtimes = movieTC.fetchShowtimes(location, movie);

        if (showtimes == null || showtimes.isEmpty()) {
            JLabel noShowtimesLabel = new JLabel("No showtimes available for this movie at this location.");
            showtimesPanel.add(noShowtimesLabel);
        } else {
            Set<String> uniqueDates = new HashSet<>();  // Collect unique dates
            for (Showtime showtime : showtimes) {
                uniqueDates.add(showtime.getDate());
            }

            // Group showtimes by date
            Map<String, ArrayList<Showtime>> showtimesGroupedByDate = new HashMap<>();
            for (String date : uniqueDates) {
                ArrayList<Showtime> dateShowtimes = new ArrayList<>();
                for (Showtime showtime : showtimes) {
                    if (showtime.getDate().equals(date)) {
                        dateShowtimes.add(showtime);
                    }
                }
                showtimesGroupedByDate.put(date, dateShowtimes);
            }

            // Make sure the layout is set to BoxLayout to stack components vertically
            showtimesPanel.setLayout(new BoxLayout(showtimesPanel, BoxLayout.Y_AXIS));

            for (Map.Entry<String, ArrayList<Showtime>> entry : showtimesGroupedByDate.entrySet()) {
                String date = entry.getKey();
                ArrayList<Showtime> dateShowtimes = entry.getValue();

                // Create a new date label and add it to the panel (this will automatically start a new row)
                JLabel dateLabel = new JLabel("<html><b>Date:</b> " + date + "</html>");
                dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align it to the left (optional)
                showtimesPanel.add(dateLabel);

                // Add showtimes as JButton for each showtime entry
                for (Showtime showtime : dateShowtimes) {
                    JButton showtimeButton = new JButton(showtime.toString());  // Using the overridden toString method
                    showtimeButton.setAlignmentX(Component.LEFT_ALIGNMENT);  // Align it to the left
                    showtimeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Handle the button click, e.g., show booking options or further details
                            System.out.println("Showtime selected: " + showtime);
                        }
                    });

                    showtimesPanel.add(showtimeButton);  // Add the button to the details panel
                }

                showtimesPanel.add(Box.createVerticalStrut(10)); // Add space between dates
            }
        }

        // Refresh the details panel to reflect the updated showtimes
        showtimesPanel.revalidate();
        showtimesPanel.repaint();
    }

}
