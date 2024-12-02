import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialPanel extends JPanel {
    private MovieTheatreApp app;
    private Color Red = new Color(139, 0, 0);
    private Color Yellow = new Color(255, 248, 191);
    private Color Orange = new Color(244, 138, 104);
    private int bWidth = 100;
    private int bHeight = 50;

    public InitialPanel(MovieTheatreApp app) {
        this.app = app;

        this.setLayout(new GridLayout(3, 1));


        this.setBackground(Yellow);
        JPanel textPanel = new JPanel(new GridLayout(2, 1));


        JLabel welcomeLabel = new JLabel("Welcome To", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        welcomeLabel.setForeground(Color.WHITE);

        JLabel acmeplexLabel = new JLabel("ACMEPLEX", JLabel.CENTER);
        acmeplexLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        acmeplexLabel.setForeground(Color.WHITE);

        textPanel.add(welcomeLabel);

        textPanel.add(acmeplexLabel);

        textPanel.setBackground(Orange);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50)); // Use FlowLayout for better control
        buttonPanel.setBackground(Red);
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(bWidth, bHeight));
        loginButton.setForeground(Red);


        JButton registerButton = new JButton("Sign Up");
        registerButton.setPreferredSize(new Dimension(bWidth, bHeight));
        registerButton.setForeground(Red);

        JButton guestButton = new JButton("Continue as Guest");
        guestButton.setPreferredSize(new Dimension(200, bHeight));
        guestButton.setForeground(Red);


        JButton adminButton = new JButton("Admin Login");
        adminButton.setPreferredSize(new Dimension(bWidth, bHeight));
        adminButton.setForeground(Red);


        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(guestButton);
        buttonPanel.setBackground(Yellow);


        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.switchToLogin();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.setFromSignUp(true);
                app.switchToRegister();
            }
        });
        guestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.setCurrentUser(0); // Indicate that the current user is a guest
                GuestPanel guest = app.getGuestPanel();
                guest.updateButtons();
                app.switchToGuest();
            }
        });
        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.switchToAdmin();
            }
        });



        JPanel adminButtonPanel =  new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        adminButtonPanel.setBackground(Yellow);
        adminButtonPanel.add(adminButton, BorderLayout.EAST);



        this.add(adminButtonPanel); // Admin button at top-right
        this.add(textPanel); // Center the text panel vertically and horizontally
        this.add(buttonPanel); // Bottom buttons centered
    }
}
