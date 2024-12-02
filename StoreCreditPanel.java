import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class StoreCreditPanel extends JPanel {
    private int width = 100;
    private int height = 30;
    private JTextField usrInput;
    private String username;
    private MovieTheatreApp app;
    private Color Red = new Color(139, 0, 0);
    private Color Yellow = new Color(255, 248, 191);
    private Color Orange = new Color(244, 138, 104);
    private JPanel creditPanel; // Right side panel

    private TicketController ticketC;

    public StoreCreditPanel(MovieTheatreApp app, UserDatabaseManager userDBM, TicketController ticketC) {
        this.app = app;
        this.ticketC = ticketC;

        JLabel usrLabel = new JLabel("Email:");
        usrLabel.setForeground(Red);

        usrInput = new JTextField(15);
        usrInput.setPreferredSize(new Dimension(width, height));

        JButton submitButton = new JButton("Submit");
        submitButton.setForeground(Red);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Toggle the visibility of the creditPanel after submitting email
                creditPanel.setVisible(true);  // Show the right-side panel

                // Handle the submitted email (you may want to add more logic here)
                String email = usrInput.getText();
                System.out.println("Email entered: " + email);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setForeground(Red);
        backButton.addActionListener(e -> {
            System.out.println("Going back to guest panel");
            app.switchToPayment();
        });

        setLayout(new BorderLayout());

        // Header panel with title
        JPanel headerPanel = new JPanel();
        JLabel title = new JLabel("Enter email associated with store credit");
        title.setForeground(Red);
        headerPanel.add(title);
        headerPanel.setBackground(Yellow);

        this.add(headerPanel, BorderLayout.NORTH);

        // Submit and back buttons at the bottom
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        submitPanel.add(backButton);
        submitPanel.add(submitButton);
        this.add(submitPanel, BorderLayout.PAGE_END);
        submitPanel.setBackground(Orange);

        // Client info panel (email field)
        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        clientPanel.setBackground(Yellow);

        int row = 0;
        addComponent(clientPanel, usrLabel, 0, row, gbc);
        addComponent(clientPanel, usrInput, 1, row++, gbc);

        this.add(clientPanel, BorderLayout.CENTER);

        // Right side "creditPanel"
        creditPanel = new JPanel();
        creditPanel.setLayout(new BorderLayout());
        creditPanel.setPreferredSize(new Dimension(200, this.getHeight()));  // Set fixed size for the panel
        creditPanel.setBackground(Color.LIGHT_GRAY);

        JButton useButton = new JButton("Use");
        useButton.setForeground(Red);
        creditPanel.add(useButton, BorderLayout.PAGE_END);

        // Initially hide the creditPanel
        creditPanel.setVisible(false);

        // Add creditPanel to the right of the main panel
        this.add(creditPanel, BorderLayout.EAST);
    }

    public void displayPurchasedTickets(String email) {
        this.email = email;
        ticketsPanel.removeAll();  // Clear existing components
        // Retrieve payments for the current registered user
        this.tickets = ticketC.getTicketsFromEmail(email);
        ticketsPanel.setLayout(new GridLayout(tickets.size(), 1, 5, 5)); // Stack components vertically


        if (tickets == null || tickets.isEmpty()) {
            submitButton.setEnabled(false);
            JLabel noTickets = new JLabel("No purchased tickets.");
            noTickets.setForeground(Red);
            noTickets.setHorizontalAlignment(SwingConstants.CENTER);
            noTickets.setVerticalAlignment(SwingConstants.CENTER);
            ticketsPanel.add(noTickets);
        } else {
            // Loop through saved payments and create a button for each
            for (Ticket ticket : tickets) {
                System.out.println("IN RU REFUND< TICKET FOR LOOP, TICKET ID IS " + ticket.getTicketID());
                if (!ticket.getRefunded()) { // ONLY SHOW NON REFUNDED ONES HOPEFULLY
                    System.out.println("TICKET NOT REFUNDED");
                    int showtimeID = ticket.getShowtimeID();
                    Showtime showtimeTemp = ticketC.getShowtimeFromID(showtimeID);
                    Movie movieTemp = showtimeTemp.getMovie();
                    Location theatreTemp = showtimeTemp.getLocation();
                    int seatID = ticket.getSeatID();
                    Seat seatTemp = ticketC.getSeatFromID(seatID);

                    JLabel cardNumLabel = new JLabel("<html>Movie: " + movieTemp.getTitle() + "<br>" +
                            "Showtime: " + showtimeTemp.getDate() + " @ " + showtimeTemp.getTime() + "<br>" +
                            "Seat: " + seatTemp.toString() + "<br>" +
                            "Cost: $12.50 <br>" +
                            ticket.toString() + "</html>");

                    cardNumLabel.setForeground(Red);
                    JButton selectTicketButton = new JButton("Select");
                    selectTicketButton.setForeground(Red);
                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));


                    buttonPanel.add(cardNumLabel);
                    buttonPanel.add(selectTicketButton);
                    buttonPanel.setBackground(Yellow);
                    selectTicketButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (currentSelectTicketButton == selectTicketButton) {
                                updateButtonColor(selectTicketButton, true);
                                currentSelectTicketButton = null;
                                submitButton.setEnabled(false);
                            } else {
                                updateButtonColor(selectTicketButton, false);
                                submitButton.setEnabled(true);
                                currentSelectTicketButton = selectTicketButton;
                                selectedTicket = ticket;
                            }
                        }
                    });


                    // Add the label and the select button for each saved payment
                    ticketsPanel.add(buttonPanel);
                }
                else {
                    submitButton.setEnabled(false);
                    JLabel noTickets = new JLabel("No purchased tickets.");
                    noTickets.setForeground(Red);
                    noTickets.setHorizontalAlignment(SwingConstants.CENTER);
                    noTickets.setVerticalAlignment(SwingConstants.CENTER);
                    ticketsPanel.add(noTickets);
                }
            }
        }

        ticketsPanel.revalidate();  // Ensure layout updates after adding components
        ticketsPanel.repaint();     // Ensure repaint of the panel
    }


    private boolean validateRefund(Showtime showtime) {
        boolean valid = false;

        String showtimeDateStr = showtime.getDate();
        String showtimeTimeStr = showtime.getTime();

        // Define format for parsing the date part of the showtime
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Parse the showtime date and time strings into LocalDate and LocalTime
        LocalDate showtimeDate = LocalDate.parse(showtimeDateStr, dateFormatter);
        LocalTime showtimeTime = LocalTime.parse(showtimeTimeStr, timeFormatter);

        // Combine date and time to form a ZonedDateTime object for showtime
        ZonedDateTime showtimeZonedDateTime = ZonedDateTime.of(showtimeDate, showtimeTime, ZoneId.of("America/Edmonton"));

        // Get the current time in Calgary (America/Edmonton time zone)
        ZonedDateTime currentZonedDateTime = ZonedDateTime.now(ZoneId.of("America/Edmonton"));

        // Calculate the difference in hours between the current time and the showtime
        long hoursDifference = ChronoUnit.HOURS.between(currentZonedDateTime, showtimeZonedDateTime);

        // Print out the details for debugging
        System.out.println("Showtime: " + showtimeZonedDateTime);
        System.out.println("Current time: " + currentZonedDateTime);
        System.out.println("Hours difference: " + hoursDifference);

        if (app.getCurrentUser() == 1) {
            // Check for logged-in user (example condition for app.getCurrentUser() == 1)
            if (currentZonedDateTime.isBefore(showtimeZonedDateTime)) {
                valid = true;
            }
        }
        else if (app.getCurrentUser() == 0) { // CHECK FOR GUEST USER
            // For guest user, check if the showtime is at least 72 hours ahead of the current time
            if (hoursDifference >= 72) {
                valid = true;
            } else {
                System.out.println("Showtime is less than 72 hours away. Hours difference: " + hoursDifference);
            }
        }

        return valid;
    }


    // Helper method for adding components to clientPanel
    private void addComponent(JPanel panel, Component component, int gridx, int gridy, GridBagConstraints gbc) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        panel.add(component, gbc);
    }
}
