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
import java.util.ArrayList;

public class StoreCreditPanel extends JPanel {
    private int width = 100;
    private int height = 30;
    private JTextField usrInput;
    private String username;
    private MovieTheatreApp app;
    private Color Red = new Color(139, 0, 0);
    private Color Yellow = new Color(255, 248, 191);
    private Color Orange = new Color(244, 138, 104);
    private final Color pastelGreen = new Color(152, 251, 152); // Light pastel green

    private JPanel creditPanel; // Right side panel

    private TicketController ticketC;
    private String email;

    private ArrayList<StoreCredit> credits;
    private JButton useButton;
    private JButton currentSelectButton;


    private StoreCredit selectedCredit;

    private JButton enterButton;

    private JPanel sidePanel;

    private JPanel usePanel;

    private JButton submitButton;


    public StoreCreditPanel(MovieTheatreApp app, UserDatabaseManager userDBM, TicketController ticketC) {
        this.app = app;
        this.ticketC = ticketC;

        JLabel usrLabel = new JLabel("Email:");
        usrLabel.setForeground(Red);

        usrInput = new JTextField(15);
        usrInput.setPreferredSize(new Dimension(width, height));

        submitButton = new JButton("Submit");
        submitButton.setForeground(Red);
        submitButton.setEnabled(false);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(validateCredit(selectedCredit) && selectedCredit != null) { // idk man:(((
                    String message = "Sucessfully purchased ticket with selected store credit. Remaining amount for selected store credit: "
                            + (selectedCredit.getAmount() - 12.50);
                    JOptionPane.showMessageDialog(app, message);
                    ticketC.changeCredit(selectedCredit, (selectedCredit.getAmount() - 12.5));
                    // NEED TO UPDATE STORE CREDIT IN DB

                }
                else {
                    JOptionPane.showMessageDialog(app, "Invalid payment. Ticket costs $12.50 in store credit");

                }
                // check if enough credit to but the ticket 12.50
                // display JOPtion panel that it successfully purchased
                // subtract that credit from their credit !!!!! and put back into db
            }
        });


        enterButton = new JButton("Enter");
        enterButton.setForeground(Red);
        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Toggle the visibility of the creditPanel after submitting email
                sidePanel.setVisible(true);  // Show the right-side panel

                // Handle the submitted email (you may want to add more logic here)
                email = usrInput.getText();
                displayCredits();
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
        addComponent(clientPanel, usrLabel, 0, row, gbc);  // FOR THE EMAILLLL
        addComponent(clientPanel, usrInput, 1, row, gbc);
        addComponent(clientPanel, enterButton, 2, row, gbc);

        this.add(clientPanel, BorderLayout.CENTER);

//        useButton = new JButton("Use");
//        useButton.setForeground(Red);
//        useButton.setEnabled(false);
//
//        usePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        usePanel.setBackground(Color.WHITE);
//        usePanel.add(useButton);

        creditPanel = new JPanel();
        creditPanel.setLayout(new GridLayout(6, 2, 5, 5));
        creditPanel.setPreferredSize(new Dimension(400, this.getHeight()));  // Set fixed size for the panel
        creditPanel.setBackground(Color.WHITE);
        creditPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidePanel = new JPanel(new BorderLayout());
        sidePanel.add(creditPanel, BorderLayout.CENTER);
        //sidePanel.add(usePanel, BorderLayout.PAGE_END);
        sidePanel.setBackground(Color.WHITE);



//        useButton = new JButton("Use");
//        useButton.setForeground(Red);
//        creditPanel.add(useButton, BorderLayout.PAGE_END);

        // Initially hide the creditPanel
        sidePanel.setVisible(false);

        // Add creditPanel to the right of the main panel
        this.add(sidePanel, BorderLayout.EAST);
    }

    public void displayCredits() {
        creditPanel.removeAll();  // Clear existing components

        // Retrieve credits for the current registered user
        this.credits = ticketC.getCreditsFromEmail(email);

        // Update the layout of the creditPanel based on the number of credits
        creditPanel.setLayout(new GridLayout(credits.size() + 1, 1, 5, 5)); // Stack components vertically

        if (credits == null || credits.isEmpty()) {
            useButton.setEnabled(false);
            JLabel noCreditsLabel = new JLabel("No available store credit.");
            noCreditsLabel.setForeground(Red);
            noCreditsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noCreditsLabel.setVerticalAlignment(SwingConstants.CENTER);
            creditPanel.add(noCreditsLabel);
        } else {
            // Loop through saved credits and create a button for each
            for (StoreCredit credit : credits) {
                JLabel cardNumLabel = new JLabel(credit.toString());
                cardNumLabel.setForeground(Red);
                JButton selectButton = new JButton("Select");
                selectButton.setForeground(Red);
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.setBackground(Color.WHITE);

                buttonPanel.add(cardNumLabel);
                buttonPanel.add(selectButton);
                buttonPanel.setBackground(Color.WHITE);

                // Button action to select a credit
                selectButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (currentSelectButton == selectButton) {
                            updateButtonColor(selectButton, true);  // Deselect
                            currentSelectButton = null;
                            selectedCredit = null;
                            submitButton.setEnabled(false);  // Disable Use button if no credit selected
                        } else {
                            updateButtonColor(selectButton, false);  // Select
                            currentSelectButton = selectButton;
                            selectedCredit = credit;  // Set selected credit
                            submitButton.setEnabled(true);  // Enable Use button
                        }
                    }
                });

                // Add the label and the select button for each credit
                creditPanel.add(buttonPanel);

            }
        }

        // Call revalidate and repaint on the creditPanel to refresh its layout
        creditPanel.revalidate();  // Ensure layout updates after adding components
        creditPanel.repaint();     // Ensure repaint of the panel
    }




    // Helper method for adding components to clientPanel
    private void addComponent(JPanel panel, Component component, int gridx, int gridy, GridBagConstraints gbc) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        panel.add(component, gbc);
    }

    public void updateButtonColor(JButton button, boolean unclicked) {
        if (unclicked) {
            button.setBackground(UIManager.getColor("Button.background"));
        } else {
            button.setBackground(pastelGreen);
        }
    }

    public void autofill() {
        if ((app.getCurrentUser() == 1) && (app.getRU() != null)) {
            usrInput.setText(app.getRU().getEmail());
        }
    }

    private boolean validateCredit(StoreCredit credit) {
        boolean valid = true;
        if (credit.getAmount() < 12.50) {
            valid = false;
        }

        return valid;
    }

}
