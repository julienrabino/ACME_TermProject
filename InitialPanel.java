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
                app.switchToGuest();
            }
        });

        this.setLayout(new FlowLayout());
        this.add(loginButton);
        this.add(registerButton);
        this.add(guestButton);
    }
}
