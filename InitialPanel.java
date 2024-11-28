import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialPanel extends JPanel {
    private MovieTheatreApp app;

    public InitialPanel(MovieTheatreApp app) {
        this.app = app;


        JButton loginButton = new JButton("login");
        JButton registerButton = new JButton("sign up");
        JButton guestButton = new JButton("continue as guest");
        JButton adminButton = new JButton("admin login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.switchToLogin();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.switchToRegister();
            }
        });
        guestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.setCurrentUser(0); // indicate whoevers using our app rn is a guest !!!
                app.switchToGuest();
            }

        });
        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.switchToAdmin();
            }
        });
        this.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the buttons
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(guestButton);

        this.add(buttonPanel, BorderLayout.CENTER);

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the admin button
        adminPanel.add(adminButton);

        // Add the adminPanel to the South section of the BorderLayout
        this.add(adminPanel, BorderLayout.SOUTH);
    }
}
