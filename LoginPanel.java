import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private int width = 100;
    private int height = 30;
    private JTextField usrInput;
    private JPasswordField pwInput;
    private String username;
    private String password;

    private MovieTheatreApp app;

    public LoginPanel(MovieTheatreApp app,UserDatabaseManager userDBM) {
        this.app = app;

        JLabel usrLabel = new JLabel("Username:");
        JLabel pwLabel = new JLabel("Password:");

        usrInput = new JTextField(15);
        pwInput = new JPasswordField(15);
        usrInput.setPreferredSize(new Dimension(width, height));
        pwInput.setPreferredSize(new Dimension(width, height));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                username = usrInput.getText();
                System.out.println(username);
                password = new String(pwInput.getPassword());
                //System.out.println("PW IN ACTION PERFORMED");
                //System.out.println(password);

                // Need to change/add to this stuff still
                if (validateLogin(userDBM)) {
                    JOptionPane.showMessageDialog(app, "Logged in successfully!");
                    usrInput.setText("");
                    pwInput.setText("");
                    app.switchToMovieList();  // switch to the movie list panel
                }
            }
        });

        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        JLabel title = new JLabel("Sign In");
        headerPanel.add(title);

        this.add(headerPanel, BorderLayout.NORTH);

        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        submitPanel.add(submitButton);
        this.add(submitPanel, BorderLayout.PAGE_END);

        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);


        int row = 0;
        addComponent(clientPanel, usrLabel, 0, row, gbc);
        addComponent(clientPanel, usrInput, 1, row++, gbc);

        addComponent(clientPanel, pwLabel, 0, row, gbc);
        addComponent(clientPanel, pwInput, 1, row++, gbc);

        this.add(clientPanel, BorderLayout.CENTER);

    }
    private void addComponent(JPanel panel, Component component, int gridx, int gridy, GridBagConstraints gbc) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        panel.add(component, gbc);
    }

    private boolean validateLogin(UserDatabaseManager userDBM) {
        boolean valid = true;
        RegisteredUser RU = userDBM.getRUFromUsername(username);
        if (RU == null) {
            valid = false;
            JOptionPane.showMessageDialog(app, "Invalid username.");
            //let user know their username doesnt exist !!
        }
        else if (RU != null) {// if it returned a register user
            String pw = RU.getPassword(); // get password from database
            //System.out.println("PW RETREIVED");
            System.out.println(pw);
            System.out.println(password);
            System.out.println(pw);
            if (!pw.equals(password)) {
                valid = false;
                JOptionPane.showMessageDialog(app, "Incorrect password.");
                //let user know wrong password!
            }
            //check if password is correct !!!!!
        }
        return valid;
    }

}