// maybe class that displays all their purhcased tickets and they can select one to refund ;-; idk
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
                // need db function to get showtime from its id. then get the time and date and whatever other info..
            }
        });

        // Submit panel that holds the buttons at the bottom
        submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Centering the button
        submitPanel.setBackground(Yellow);
        submitPanel.add(submitButton);


        setLayout(new GridLayout(3, 1, 10, 10));
        this.add(title, BorderLayout.NORTH); // Add label at the top
        this.add(ticketsPanel, BorderLayout.CENTER); // Add saved payments panel
        this.add(submitPanel, BorderLayout.PAGE_END); // Add submit panel at the bottom
    }

    public void displayPurchasedTickets(String email) {
        ticketsPanel.removeAll();  // Clear existing components
        // Retrieve payments for the current registered user
        this.tickets = ticketC.getTicketsFromEmail(email);
        ticketsPanel.setLayout(new GridLayout(tickets.size(), 1, 5, 5)); // Stack components vertically


        if (tickets == null || tickets.isEmpty()) {
            JLabel noTickets = new JLabel("No purchased tickets.");
            noTickets.setForeground(Red);
            noTickets.setHorizontalAlignment(SwingConstants.CENTER);
            noTickets.setVerticalAlignment(SwingConstants.CENTER);
            ticketsPanel.add(noTickets);
        } else {
            // Loop through saved payments and create a button for each
            for (Ticket ticket : tickets) {
                JLabel cardNumLabel = new JLabel("Card ending in: " + ticket.toString());  // Display payment details
                cardNumLabel.setForeground(Red);
                JButton selectTicketButton = new JButton("Select");
                selectTicketButton.setForeground(Red);

                JPanel buttonPanel = new JPanel( new FlowLayout(FlowLayout.CENTER));
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
        }

        ticketsPanel.revalidate();  // Ensure layout updates after adding components
        ticketsPanel.repaint();     // Ensure repaint of the panel
    }

    public void updateButtonColor(JButton button, boolean unclicked) {
        if (unclicked) {
            button.setBackground(UIManager.getColor("Button.background"));
        } else {
            button.setBackground(pastelGreen);
        }
    }
}
