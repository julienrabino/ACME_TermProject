import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupPanel extends JPanel {
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

    public SignupPanel(MovieTheatreApp app, UserDatabaseManager userDBM) {
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
