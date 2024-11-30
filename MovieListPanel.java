import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MovieListPanel extends JPanel {
    private ArrayList<Movie> movies;
    private ArrayList<Location> locations;
    private boolean showAll = true;
    private boolean search = false;
    private JComboBox<Location> locationComboBox;
    private Movie lastSelectedMovie = null;
    private JButton submitButton;

    private String searchMovie;
    private JTextField searchInput;
    private JList<Movie> movieList;
    private DefaultListModel<Movie> listModel;
    private JButton showAllButton;
    private JLabel movieDetailsLabel;
    private JPanel detailsPanel;
    private JPanel showtimesPanel;
    private JPanel noLocationsPanel;
    private JPanel seatPanel;
    private JButton currentSeatButton;
    private JButton currentShowtimeButton;
    private MovieTheatreController movieTC;
    private MovieTheatreApp app;
    private final Color Red = new Color(139, 0, 0);
    private final Color Yellow = new Color(255, 248, 191);
    private final Color Orange = new Color(244, 138, 104);
    private final Color pastelGreen = new Color(152, 251, 152); // Light pastel green

    public MovieListPanel(MovieTheatreApp app, MovieTheatreController movieTC) {

        this.app = app;
        this.movieTC = movieTC;

        // Set layout for the main panel
        this.setLayout(new BorderLayout());

        // Back button at the bottom
        JButton backButton = new JButton("Back");
        backButton.setForeground(Red);
        backButton.addActionListener(e -> {
            if(app.getCurrentUser() == 0){
                System.out.println("Going to Guest view.");
                seatPanel.removeAll();
                seatPanel.setVisible(false);
                updateButtonColor(currentShowtimeButton, true);
                currentShowtimeButton = null;
                app.setSelectedShowtime(null);
                submitButton.setVisible(false);
                app.switchToGuest();
            } else if (app.getCurrentUser() == 1){
                System.out.println("Going to initial view.");
                app.setCurrentUser(0);
                app.setRU(null);
                System.out.println("Logging out");
                seatPanel.removeAll();
                seatPanel.setVisible(false);
                updateButtonColor(currentShowtimeButton, true);
                currentShowtimeButton = null;
                app.setSelectedShowtime(null);
                submitButton.setVisible(false);
                app.switchToInitial();
            }


            });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(backButton);
        bottomPanel.setBackground(Yellow);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // Search bar at the top
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Yellow);

        JLabel searchLabel = new JLabel("Search a Movie:");
        searchLabel.setForeground(Red);

        searchInput = new JTextField(30);
        JButton searchButton = new JButton("Search");
        searchButton.setForeground(Red);

        showAllButton = new JButton("Show All");
        showAllButton.setForeground(Red);
        showAllButton.setVisible(false);

        searchButton.addActionListener(e -> {
            submitButton.setVisible(false);
            searchMovie = searchInput.getText();
            search = true;
            showAll = false;
            updateMovieList();
            showAllButton.setVisible(true);
        });

        showAllButton.addActionListener(e -> {
            submitButton.setVisible(false);
            search = false;
            showAll = true;
            searchInput.setText(""); // Clear the search bar
            updateMovieList();
            showAllButton.setVisible(false);
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchInput);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        this.add(searchPanel, BorderLayout.NORTH);

        // Movie list in the center
        listModel = new DefaultListModel<>();
        movieList = new JList<>(listModel);
        movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane movieListScrollPane = new JScrollPane(movieList);
        this.add(movieListScrollPane, BorderLayout.CENTER);

        // Details panel on the right
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Yellow);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Movie details label
        movieDetailsLabel = new JLabel("Movie Details");
        movieDetailsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.add(movieDetailsLabel);
        detailsPanel.add(Box.createVerticalStrut(10)); // Add spacing

        // Location dropdown
        locations = new ArrayList<>();
        locationComboBox = new JComboBox<>(locations.toArray(new Location[0]));
        locationComboBox.setPreferredSize(new Dimension(200, 30));
        locationComboBox.setMaximumSize(locationComboBox.getPreferredSize());
        locationComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        locationComboBox.addActionListener(e -> {
            submitButton.setVisible(false);
            Location selectedLocation = (Location) locationComboBox.getSelectedItem();
            app.setTheatre(selectedLocation);
            System.out.println("Selected Location: " + selectedLocation);
            showtimesPanel.removeAll();
            seatPanel.removeAll();
            detailsPanel.add(showtimesPanel);
            displayShowtimes(selectedLocation, lastSelectedMovie);
        });

        detailsPanel.add(locationComboBox);
        detailsPanel.add(Box.createVerticalStrut(10)); // Add spacing


        // No Locations Panel
        noLocationsPanel = new JPanel();
        noLocationsPanel.setLayout(new BoxLayout(noLocationsPanel, BoxLayout.Y_AXIS)); // Align vertically
        noLocationsPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align with the left edge of the detailsPanel
        noLocationsPanel.setBackground(Yellow); // Match the background color
        noLocationsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding

        JLabel noLocationsLabel = new JLabel("<html><b>No Theatres are showing this Movie.</b></html>");
        noLocationsLabel.setForeground(Red);
        noLocationsLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align text with the panel
        noLocationsPanel.add(noLocationsLabel);

        noLocationsPanel.setVisible(false);
        detailsPanel.add(noLocationsPanel);


        // Showtimes panel
        showtimesPanel = new JPanel();
        showtimesPanel.setLayout(new BoxLayout(showtimesPanel, BoxLayout.Y_AXIS));
        showtimesPanel.setBackground(Yellow);;
        showtimesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.add(showtimesPanel);
        detailsPanel.add(Box.createVerticalStrut(10)); // Add spacing

        // Seat panel
        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(0, 5, 5, 5));
        seatPanel.setBackground(Orange);
        seatPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        seatPanel.setVisible(false);

        detailsPanel.add(seatPanel);
        detailsPanel.add(Box.createVerticalStrut(10)); // Add spacing

        // Submit button
        submitButton = new JButton("Book Now");
        submitButton.setForeground(Red);
        submitButton.setVisible(false);
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitButton.addActionListener(e -> {
            System.out.println("seat: " + app.getSelectedSeat());
            System.out.println("showtime: " + app.getSelectedShowtime());
            System.out.println("movie: " + app.getMovie());
            System.out.println("loc: " + app.getTheatre());
            ConfirmPanel confirm = app.getConfirmPanel();
            confirm.getInfo();
            app.switchToConfirm();
        });

        detailsPanel.add(submitButton);

        // Add details panel to the main layout
        this.add(detailsPanel, BorderLayout.EAST);

        // Initialize the movie list
        updateMovieList();
        currentSeatButton = null;
        currentShowtimeButton = null;
    }

    private void updateMovieList() {
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

    private void addMovieSelectionListener(MovieTheatreController movieTC) {
        movieList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seatPanel.setVisible(false);
                noLocationsPanel.setVisible(false);
                Movie selectedMovie = movieList.getSelectedValue();

                if (selectedMovie != null) {
                    if (lastSelectedMovie != null && lastSelectedMovie.equals(selectedMovie)) {
                        return;
                    }
                    seatPanel.setVisible(false);
                    seatPanel.removeAll();
                    showtimesPanel.removeAll();
                    locationComboBox.setVisible(true);
                    lastSelectedMovie = selectedMovie;
                    app.setMovie(lastSelectedMovie);
                    detailsPanel.setVisible(true);

                    String movieDetails = "<html><b>Title:</b> " + selectedMovie.getTitle() +
                            "<br><b>Genre:</b> " + selectedMovie.getGenre() + "</html>";
                    movieDetailsLabel.setText(movieDetails);

                    ArrayList<Location> loc = movieTC.getMovieLocations(selectedMovie);

                    locations.clear();
                    if (!loc.isEmpty()) {
                        locations.addAll(loc);
                    } else{
                        System.out.println("No theatres available.");

                        noLocationsPanel.setVisible(true);
                        locationComboBox.setVisible(false);
                        detailsPanel.revalidate();
                        detailsPanel.repaint();
                        return;
                    }

                    locationComboBox.setModel(new DefaultComboBoxModel<>(locations.toArray(new Location[0])));
                    if (!locations.isEmpty()) {
                        locationComboBox.setSelectedIndex(0);

                    }
                } else {
                    detailsPanel.setVisible(false);
                }
                detailsPanel.revalidate();
                detailsPanel.repaint();
            }
        });
    }
    public void displayShowtimes(Location location, Movie movie) {
        showtimesPanel.removeAll();  // Clear old components
        ArrayList<Showtime> showtimes = movieTC.fetchShowtimes(location, movie);

        if (showtimes == null || showtimes.isEmpty()) {
            JLabel noShowtimesLabel = new JLabel("No showtimes available for this movie at this location.");
            noShowtimesLabel.setForeground(Red);
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
                    JButton showtimeButton = new JButton(showtime.toString());// Using the overridden toString method
                    updateButtonColor(showtimeButton, true);
                    showtimeButton.setAlignmentX(Component.LEFT_ALIGNMENT);  // Align it to the left
                    showtimeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (currentShowtimeButton == showtimeButton){
                                if (currentShowtimeButton.getBackground().equals(pastelGreen)){
                                    // means the button was prev Selected, now user wants to unclick
                                    updateButtonColor(showtimeButton, true); // making the button default bg
                                    currentShowtimeButton = null; // no selected showtime
                                    System.out.println("Deselected showtime:" + app.getSelectedShowtime().getShowtimeID());
                                    seatPanel.removeAll();
                                    seatPanel.setVisible(false);
                                    app.setSelectedShowtime(null);
                                    submitButton.setVisible(false);
                                    return;

                                }else {
                                    // means the button wasn't prev selected, now user wants to select it
                                    updateButtonColor(showtimeButton, false);
                                }
                            }

                            System.out.println("Showtime clicked: " + showtime);
                            updateButtonColor(showtimeButton, false);
                            if (currentShowtimeButton != null) {
                                updateButtonColor(currentShowtimeButton, true);
                            }
                            app.setSelectedShowtime(showtime);
                            submitButton.setVisible(false);
                            seatPanel.setVisible(false);

                            currentShowtimeButton = showtimeButton;  // Update the current showtime button

                            // Debugging: Check selected showtime details
                            System.out.println("Showtime selected ID: " + app.getSelectedShowtime().getShowtimeID());
                            System.out.println("Showtime selected date/time: " + app.getSelectedShowtime().getDate() + " " + app.getSelectedShowtime().getTime());
                            displaySeatMap(showtime);  // Show seat map for the selected showtime
                            seatPanel.setVisible(true);  // Make seat panel visible
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



    public void displaySeatMap(Showtime showtime) {
        seatPanel.removeAll();

        ArrayList<Seat> seats = movieTC.fetchSeats(showtime.getShowtimeID());
        if (seats == null || seats.isEmpty()) {
            JLabel noSeats = new JLabel("No seats available for this showtime.");
            noSeats.setForeground(Red);
            seatPanel.add(noSeats);
        } else {
            seatPanel.setLayout(new GridLayout(0, 5, 5, 5));

            for (Seat seat : seats) {
                JButton seatButton = new JButton();
                seatButton.setForeground(Red);
                if (!seat.getAvailable()) {
                    //seatButton.setBackground(Red);
                    seatButton.setEnabled(false);
                }
                seatButton.setText(seat.toString());
//
                if (!seat.getAvailable()) {
                    seatButton.setEnabled(false);
                }
                if (seat.isAnRUSeat()) {
                    if (app.getCurrentUser() == 0) {
                        seatButton.setEnabled(false);
                    }
                }

                seatButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (currentSeatButton == seatButton) {
                            if (currentSeatButton.getBackground().equals(pastelGreen)) {
                                updateButtonColor(seatButton, true); // making the button default bg
                                currentSeatButton = null;

                                System.out.println("Deselected seat:" + app.getSelectedSeat().getSeatID());
                                app.setSelectedSeat(null);
                                submitButton.setVisible(false);

                                return;
                            } else {
                                updateButtonColor(seatButton, false);
                            }
                        }
                        app.setSelectedSeat(seat);
                        submitButton.setVisible(true);
                        seatButton.setBackground(pastelGreen);
                        System.out.println("Seat selected ID: " + app.getSelectedSeat().getSeatID());
                        System.out.println("Seat selected: " + app.getSelectedSeat().getSeatRow() + app.getSelectedSeat().getSeatCol());
                        if (currentSeatButton != null) {
                            currentSeatButton.setBackground(UIManager.getColor("Button.background"));
                        }
                        currentSeatButton = seatButton;

                    }

                });

                seatPanel.add(seatButton);

                seatPanel.revalidate();
                seatPanel.repaint();

                if (!detailsPanel.isAncestorOf(seatPanel)) {
                    detailsPanel.add(seatPanel, BorderLayout.SOUTH);
                }

                detailsPanel.revalidate();
                detailsPanel.repaint();

            }
        }
    }


    public void updateButtonColor(JButton button, boolean unclicked){
        if (unclicked){
            button.setBackground(UIManager.getColor("Button.background"));
        } else
        {
            button.setBackground(pastelGreen);
        }
    }

}
