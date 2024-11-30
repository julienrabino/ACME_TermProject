import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class PaymentPanel extends JPanel {
    private TicketController ticketC;
    private MovieTheatreApp app;
    private Color Red = new Color(139, 0, 0);
    private Color Yellow = new Color(255, 248, 191);
    private Color Orange = new Color(244, 138, 104);
    private int bWidth = 100;
    private int bHeight = 50;

    private JTextField fnameField;
    private JTextField lnameField;
    private JTextField cardNumField;
    private JTextField expiryDateField;
    private JTextField securityCodeField;
    private JTextField emailField;

    private JLabel fnameLabel;
    private JLabel lnameLabel;
    private JLabel cardNumLabel;
    private JLabel expiryDateLabel;
    private JLabel securityCodeLabel;
    private JLabel emailLabel;

    private JButton submitButton;


    public PaymentPanel(MovieTheatreApp app, UserDatabaseManager userDBM, TicketController ticketC) {
        this.ticketC = ticketC;
        this.app = app;
        this.setLayout(new BorderLayout());
        this.setBackground(Yellow);

        fnameField = new JTextField(20);
        lnameField = new JTextField(20);
        cardNumField = new JTextField(20);
        expiryDateField = new JTextField(20);
        securityCodeField = new JTextField(20);
        emailField = new JTextField(20);

        fnameLabel = new JLabel("First Name: ");
        lnameLabel = new JLabel("Last Name: ");
        cardNumLabel = new JLabel("Card Number: ");
        expiryDateLabel = new JLabel("Expiry Date (MM/YY): ");
        securityCodeLabel = new JLabel("Security Code: ");
        emailLabel = new JLabel("Email: ");

        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        JLabel title = new JLabel("Please Fill Out All Fields: ");
        title.setHorizontalAlignment(SwingConstants.CENTER);  // Center horizontally
        title.setVerticalAlignment(SwingConstants.CENTER);    // Center vertically
        title.setForeground(Red);
        headerPanel.add(title);
        headerPanel.setBackground(Yellow);
        this.add(headerPanel, BorderLayout.NORTH);

        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        clientPanel.setBackground(Yellow);
        submitButton = new JButton("Submit");
        submitButton.setForeground(Red);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean Reg = false;
                int ruid = -1;
                if (app.getRU() != null) {
                    Reg = true;
                    ruid = app.getRU().getID();
                }


                BillingSystem billingS = ticketC.getBillingSystem();
                Payment payment = new Payment(ruid, fnameField.getText(), lnameField.getText(), cardNumField.getText(), expiryDateField.getText(), securityCodeField.getText());
                billingS.addPayment(payment);// STILL NEED TO DO VALIDATION IN THIS METHOD
                System.out.println("JUST ADDED PAYMENT PANEL PAYMENT TO DB");
                Payment payment1 = billingS.getPaymentFromCard(payment.getCardNum());
                app.setSelectedPayment(payment1);
                Ticket ticket = new Ticket(app.getSelectedShowtime(), app.getSelectedSeat(), app.getRU(), emailField.getText(), 12.50, app.getSelectedPayment(),  Reg, false);
                ticketC.addTicket(ticket);
                ticketC.changeSeatAvailability(app.getSelectedSeat(), false);
                String message = "Ticket purchased successfully! Sent to " + ticket.getEmail();
                JOptionPane.showMessageDialog(app, message);
            }
        });


        int row = 0;
        addComponent(clientPanel, fnameLabel, 0, row, gbc);
        addComponent(clientPanel, fnameField, 1, row++, gbc);

        addComponent(clientPanel, lnameLabel, 0, row, gbc);
        addComponent(clientPanel, lnameField, 1, row++, gbc);

        addComponent(clientPanel, emailLabel, 0, row, gbc);
        addComponent(clientPanel, emailField, 1, row++, gbc);

        addComponent(clientPanel, cardNumLabel, 0, row, gbc);
        addComponent(clientPanel, cardNumField, 1, row++, gbc);

        addComponent(clientPanel, expiryDateLabel, 0, row, gbc);
        addComponent(clientPanel, expiryDateField, 1, row++, gbc);

        addComponent(clientPanel, securityCodeLabel, 0, row, gbc);
        addComponent(clientPanel, securityCodeField, 1, row++, gbc);

        this.add(clientPanel, BorderLayout.CENTER);

        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        submitPanel.add(submitButton);
        submitPanel.setBackground(Orange);

        this.add(submitPanel, BorderLayout.PAGE_END);

    }
    public void autofillRU() {
        if(app.getRU() != null) {
            System.out.println("RU RETRIEVED");
            RegisteredUser RU = app.getRU();
            System.out.println(RU.getFname());
            fnameField.setText(RU.getFname());
            lnameField.setText(RU.getLname());
            emailField.setText(RU.getEmail());
        }
    }
    private void addComponent(JPanel panel, Component component, int gridx, int gridy, GridBagConstraints gbc) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        panel.add(component, gbc);
    }

}
