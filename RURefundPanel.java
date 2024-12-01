// maybe class that displays all their purhcased tickets and they can select one to refund ;-; idk
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class RURefundPanel extends JPanel {
    private final Color Red = new Color(139, 0, 0);
    private final Color Yellow = new Color(255, 248, 191);
    private final Color pastelGreen = new Color(152, 251, 152); // Light pastel green

    private JButton currentSelectTicketButton;
    private JButton submitButton;
    private JLabel title;
    private JPanel ticketsPanel;

    private JPanel submitPanel;
    private MovieTheatreApp app;
    private UserDatabaseManager userDBM;
    private ArrayList<Ticket> tickets;
    private Ticket selectedTicket;

    private TicketController ticketC;

    private Showtime showtime;
    private Movie movie;
    private Location theatre;

    private Seat seat;

    private String email;

    public RURefundPanel(MovieTheatreApp app, UserDatabaseManager userDBM, TicketController ticketC) {
        this.app = app;
        this.userDBM = userDBM;
        this.ticketC = ticketC;

        this.setBackground(Yellow);

        // Initialize saved payments panel with vertical layout
        ticketsPanel = new JPanel();
        ticketsPanel.setLayout(new GridLayout(6, 2, 5, 5)); // Stack components vertically
        ticketsPanel.setBackground(Yellow);
        ticketsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Label above the saved payments
        title = new JLabel("Past purchased tickets:");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Red);

        // "Pay" button
        submitButton = new JButton("Refund");
        submitButton.setForeground(Red);
        submitButton.setEnabled(false); // Disabled initially until a selection is made
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //  NEED TO CHECK DATE OF SHOWTIME AND GET CURRENT DATE AND SEE IF U CAN REFUND IT
                int showtimeID = selectedTicket.getShowtimeID();
                showtime = ticketC.getShowtimeFromID(showtimeID);
                movie = showtime.getMovie();
                theatre = showtime.getLocation();
                int seatID = selectedTicket.getSeatID();
                seat = ticketC.getSeatFromID(seatID);

                if (validateRefund(showtime)) {
                    // UMMM MAYBE ADD WHAT CARD NUMBER IT REFUNDED TO IDK
                    JOptionPane.showMessageDialog(app, "$12.50 sucessfully refunded to " + email);

                    ticketC.changeRefunded(selectedTicket, true);
                    displayPurchasedTickets(email);
                    ticketC.changeSeatAvailability(seat, true);

                    System.out.println("VALID TICKET FOR REFUND. DO REFUND NOW ");
                }
                else {
                    JOptionPane.showMessageDialog(app, "Valid time for refund has passed.");

                    System.out.println("NOT VALID TICKET FOR REFUND ");

                }
                // need db function to get showtime from its id. then get the time and date and whatever other info..
            }
        });

        // back button
        JButton backButton = new JButton("Back");
        backButton.setForeground(Red);
        backButton.addActionListener(e -> {
            System.out.println("Going back to guest panel");
            app.switchToGuest();
        });
        // Submit panel that holds the buttons at the bottom
        submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Centering the button
        submitPanel.setBackground(Yellow);
        submitPanel.add(backButton);
        submitPanel.add(submitButton);


        setLayout(new GridLayout(3, 1, 10, 10));
        this.add(title, BorderLayout.NORTH); // Add label at the top
        this.add(ticketsPanel, BorderLayout.CENTER); // Add saved payments panel
        this.add(submitPanel, BorderLayout.PAGE_END); // Add submit panel at the bottom
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


    public void updateButtonColor(JButton button, boolean unclicked) {
        if (unclicked) {
            button.setBackground(UIManager.getColor("Button.background"));
        } else {
            button.setBackground(pastelGreen);
        }
    }
}
