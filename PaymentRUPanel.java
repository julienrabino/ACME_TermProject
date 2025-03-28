import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PaymentRUPanel extends JPanel {
    private final Color Red = new Color(139, 0, 0);
    private final Color Yellow = new Color(255, 248, 191);
    private final Color pastelGreen = new Color(152, 251, 152); // Light pastel green

    private JButton currentSelectPaymentButton;
    private JButton submitButton;
    private JButton useAnotherPaymentButton;
    private JLabel selectPaymentLabel;
    private JPanel savedPaymentsPanel;

    private JPanel submitPanel;
    private MovieTheatreApp app;
    private UserDatabaseManager userDBM;
    private ArrayList<Payment> payments;
    private Payment selectedPayment;

    private TicketController ticketC;

    public PaymentRUPanel(MovieTheatreApp app, UserDatabaseManager userDBM, TicketController ticketC) {
        this.app = app;
        this.userDBM = userDBM;
        this.ticketC = ticketC;

        this.setBackground(Yellow);

        // Initialize saved payments panel with vertical layout
        savedPaymentsPanel = new JPanel();
        savedPaymentsPanel.setLayout(new GridLayout(6, 2, 5, 5)); // Stack components vertically
        savedPaymentsPanel.setBackground(Yellow);
        savedPaymentsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Label above the saved payments
        selectPaymentLabel = new JLabel("Select a saved payment:");
        selectPaymentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        selectPaymentLabel.setForeground(Red);

        // "Pay" button
        submitButton = new JButton("Pay");
        submitButton.setForeground(Red);
        submitButton.setEnabled(false); // Disabled initially until a selection is made
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the action for using another payment method
                app.setSelectedPayment(selectedPayment);
                LocalDate currentDate = LocalDate.now();
                LocalTime currentTime = LocalTime.now();
                String timePurchased = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                String datePurchased = currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
                System.out.println("payed");
                System.out.println(selectedPayment.toString());
                Ticket ticket = new Ticket(app.getSelectedShowtime(), app.getSelectedSeat(), app.getRU(), app.getRU().getEmail(), 12.50, app.getSelectedPayment(), datePurchased, timePurchased, true, false);
                ticketC.changeSeatAvailability(app.getSelectedSeat(), false);
                ticketC.addTicket(ticket);
                String message = "Ticket purchased successfully! Sent to " + ticket.getEmail();
                JOptionPane.showMessageDialog(app, message);
                MovieListPanel movieListPanel = app.getMovieListPanel();
                movieListPanel.displaySeatMap(app.getSelectedShowtime());
                app.switchToMovieList();
            }
        });

        // "Use another payment method" button
        useAnotherPaymentButton = new JButton("Use another payment method");
        useAnotherPaymentButton.setForeground(Red);
        useAnotherPaymentButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        useAnotherPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the action for using another payment method
                System.out.println("Redirecting to another payment method...");
                PaymentPanel pay = app.getPaymentPanel();
                pay.autofillRU();
                app.switchToPayment();
                // Possibly switch to a new payment screen or form
            }
        });

        // Submit panel that holds the buttons at the bottom
        submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Centering the button
        submitPanel.setBackground(Yellow);
        submitPanel.add(submitButton);
        submitPanel.add(useAnotherPaymentButton);

        setLayout(new GridLayout(3, 1, 10, 10));
        this.add(selectPaymentLabel, BorderLayout.NORTH); // Add label at the top
        this.add(savedPaymentsPanel, BorderLayout.CENTER); // Add saved payments panel
        this.add(submitPanel, BorderLayout.PAGE_END); // Add submit panel at the bottom
    }

    public void displaySavedPayments(RegisteredUser RU) {
        savedPaymentsPanel.removeAll();  // Clear existing components
        // Retrieve payments for the current registered user
        this.payments = userDBM.getPayment(app.getRU());
        savedPaymentsPanel.setLayout(new GridLayout(payments.size(), 1, 5, 5)); // Stack components vertically


        if (payments == null || payments.isEmpty()) {
            JLabel noPayments = new JLabel("No saved payments.");
            noPayments.setForeground(Red);
            noPayments.setHorizontalAlignment(SwingConstants.CENTER);
            noPayments.setVerticalAlignment(SwingConstants.CENTER);
            savedPaymentsPanel.add(noPayments);
        } else {
            // Loop through saved payments and create a button for each
            for (Payment payment : payments) {
                JLabel cardNumLabel = new JLabel("Card: " + payment.toString());  // Display payment details
                cardNumLabel.setForeground(Red);
                JButton selectPaymentButton = new JButton("Select");
                selectPaymentButton.setForeground(Red);

                JPanel buttonPanel = new JPanel( new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(cardNumLabel);
                buttonPanel.add(selectPaymentButton);
                buttonPanel.setBackground(Yellow);
                selectPaymentButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (currentSelectPaymentButton == selectPaymentButton) {
                            updateButtonColor(selectPaymentButton, true);
                            currentSelectPaymentButton = null;
                            submitButton.setEnabled(false);
                        } else {
                            updateButtonColor(selectPaymentButton, false);
                            submitButton.setEnabled(true);
                            currentSelectPaymentButton = selectPaymentButton;
                            selectedPayment = payment;
                        }
                    }
                });

                // Add the label and the select button for each saved payment
                savedPaymentsPanel.add(buttonPanel);
            }
        }

        savedPaymentsPanel.revalidate();  // Ensure layout updates after adding components
        savedPaymentsPanel.repaint();     // Ensure repaint of the panel
    }

    public void updateButtonColor(JButton button, boolean unclicked) {
        if (unclicked) {
            button.setBackground(UIManager.getColor("Button.background"));
        } else {
            button.setBackground(pastelGreen);
        }
    }
}
