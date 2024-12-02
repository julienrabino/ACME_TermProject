import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.regex.*;

public class SignupPanel extends JPanel {
    private String username;
    private String password;
    private String passwordConfirm;
    private String email;
    private String address;
    private String Fname;
    private String Lname;
    private LocalDate expiryDate;
    private LocalDate joinDate;
    private boolean savedPayment;
    private boolean feePaid = false;
    private Color Red = new Color(139, 0, 0);
    private Color Yellow = new Color(255, 248, 191);
    private Color Orange = new Color(244, 138, 104);

    private RegisteredUser RU;

    private int width = 100;
    private int height = 30;

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
        app.setAnnualFeePaid(false);
        JLabel usrLabel = new JLabel("Username:");
        usrLabel.setForeground(Red);
        JLabel pwLabel = new JLabel("Password:");
        pwLabel.setForeground(Red);
        JLabel pwConfirmLabel = new JLabel("Confirm Password:");
        pwConfirmLabel.setForeground(Red);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Red);
        JLabel fnameLabel = new JLabel("First Name:");
        fnameLabel.setForeground(Red);
        JLabel lnameLabel = new JLabel("Last Name:");
        lnameLabel.setForeground(Red);
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setForeground(Red);

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

        //submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setForeground(Red);
        //submitButton.setEnabled(false);
        System.out.println(app.isAnnualFeePaid());
        if (app.isAnnualFeePaid()){
            submitButton.setEnabled(true);
        }
        else{
            submitButton.setEnabled(false);
        }
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int safe = 1;
                username = usrInput.getText();
                password = new String(pwInput.getPassword());
                passwordConfirm = new String(pwConfirmInput.getPassword());
                Fname = new String(fnameInput.getText());
                Lname = new String(lnameInput.getText());
                email = new String(emailInput.getText());
                address = new String(addressInput.getText());
                joinDate = LocalDate.now();
                expiryDate = joinDate.plusYears(1);
                savedPayment = false; // MAYBE MAKE A BUTTON SO ITS OPTIONAL TO ADD YOUR PAYMENT INFO !

                RU = new RegisteredUser(Fname, Lname,username, password, email, address, expiryDate, joinDate, savedPayment);

                if (validateSignup(userDBM)) {
                    userDBM.insertRU(RU);
                    // add to database
                    app.setRU(RU);
                    app.setCurrentUser(1); // indicate whoevers using our app rn is a RU
                    JOptionPane.showMessageDialog(app, "Sign up successful! Please login with your credentials.");
                    app.switchToInitial();
                    fnameInput.setText("");
                    lnameInput.setText("");
                    emailInput.setText("");
                    addressInput.setText("");
                    usrInput.setText("");
                    pwInput.setText("");
                    pwConfirmInput.setText("");
                }



            }
        });

        // pay annual fee button
        JButton paymentButton = new JButton("Pay Annual Fee");
        paymentButton.setForeground(Red);
        paymentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //feePaid = true;
                
                app.switchToPayAnnualFee(); 
                //submitButton.setEnabled(true);
            }
        });

        // JButton submitButton = new JButton("Submit");
        // submitButton.setForeground(Red);
        // if (feePaid){
        //     submitButton.setEnabled(true);
        // }
        // else{
        //     submitButton.setEnabled(false);
        // }
        // //submitButton.setEnabled(false);
        // submitButton.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         int safe = 1;
        //         username = usrInput.getText();
        //         password = new String(pwInput.getPassword());
        //         passwordConfirm = new String(pwConfirmInput.getPassword());
        //         Fname = new String(fnameInput.getText());
        //         Lname = new String(lnameInput.getText());
        //         email = new String(emailInput.getText());
        //         address = new String(addressInput.getText());
        //         joinDate = LocalDate.now();
        //         expiryDate = joinDate.plusYears(1);
        //         savedPayment = false; // MAYBE MAKE A BUTTON SO ITS OPTIONAL TO ADD YOUR PAYMENT INFO !

        //         RU = new RegisteredUser(Fname, Lname,username, password, email, address, expiryDate, joinDate, savedPayment);

        //         if (validateSignup(userDBM)) {
        //             userDBM.insertRU(RU);
        //             // add to database
        //             app.setRU(RU);
        //             app.setCurrentUser(1); // indicate whoevers using our app rn is a RU
        //             JOptionPane.showMessageDialog(app, "Sign up successful! Please login with your credentials.");
        //             app.switchToInitial();
        //             fnameInput.setText("");
        //             lnameInput.setText("");
        //             emailInput.setText("");
        //             addressInput.setText("");
        //             usrInput.setText("");
        //             pwInput.setText("");
        //             pwConfirmInput.setText("");
        //         }



        //     }
        // });

        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        JLabel title = new JLabel("Please Fill Out All Fields: ");
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
        submitPanel.add(paymentButton);
        submitPanel.add(submitButton);
        submitPanel.setBackground(Orange);

        this.add(submitPanel, BorderLayout.PAGE_END);
    }
    // Utility method to add components to the GridBagLayout
    private void addComponent(JPanel panel, Component component, int gridx, int gridy, GridBagConstraints gbc) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        panel.add(component, gbc);
    }
    public void refresh(){ //dynamically updates feePaid variable
        
    }

    private boolean validateSignup(UserDatabaseManager userDBM) {
        boolean valid = true;
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        if (!Pattern.matches(passwordRegex, password)) {
            valid = false;
            JOptionPane.showMessageDialog(app, "Password must contain at least one uppercase letter, one lowercase letter, and one number.");
        }
        if (!password.equals(passwordConfirm)) {
            valid = false;
            JOptionPane.showMessageDialog(app, "Passwords do not match.");
        }
        if(userDBM.usernameExists(RU.getUsername())) {
            valid = false;
            JOptionPane.showMessageDialog(app, "Username already in-use.");
        }
        String emailRegex = "^[a-zA-Z0-9_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.matches(emailRegex, RU.getEmail())) {
            valid = false;
            JOptionPane.showMessageDialog(app, "Invalid email format. Only letters, numbers, '_', '.', and '@' are allowed.");
        }
        // check if username already exists
        return valid;
    }

}
