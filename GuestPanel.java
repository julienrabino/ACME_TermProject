import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuestPanel extends JPanel {
    private Color Red = new Color(139, 0, 0);
    private Color Yellow = new Color(255, 248, 191);
    private Color Orange = new Color(244, 138, 104);

    private JButton logoutButton;
    private JButton backButton;
    private JButton refundButton;
    private JButton renewButton;
    private MovieTheatreApp app;

    public GuestPanel(MovieTheatreApp app, MovieTheatreController movieTC) {
        this.app = app;

        // Use BoxLayout to align buttons vertically
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Yellow);

        // Browse Movies Button
        JButton browseMovies = new JButton("Browse Movies");
        browseMovies.setForeground(Red);
        browseMovies.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.getMovieListPanel().updateMovieList();
                app.switchToMovieList();
            }
        });

        JPanel browseMoviesPanel = new JPanel();
        browseMoviesPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centering the button
        browseMoviesPanel.setBackground(Yellow);
        browseMoviesPanel.add(browseMovies);

        // Add Browse Movies Panel to the main panel
        this.add(browseMoviesPanel);

        // Refund Button (added directly below Browse Movies)
        refundButton = new JButton("Refund");
        refundButton.setForeground(Red);
        //refundButton.setBackground(Yellow);

        refundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (app.getCurrentUser() == 0){
                    app.switchToOURefundPanel();
                }
                else {
                    RURefundPanel refund = app.getRURefundPanel();
                    refund.displayPurchasedTickets(app.getRU().getEmail());
                    app.switchToRURefundPanel();
                }
//                // Reset user state if needed (this logic is likely incorrect, see below)
//                app.switchToInitial();
//                app.setCurrentUser(0);
//                app.setRU(null);
//                app.switchToInitial();
            }
        });

        JPanel refundPanel = new JPanel();
        refundPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centering the button
        refundPanel.setBackground(Yellow);
        refundPanel.add(refundButton);

        // Add Refund Button Panel to the main panel
        this.add(refundPanel);

        // renew membership button
        renewButton = new JButton("Renew Membership");
        renewButton.setForeground(Red);
        renewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                app.setFromSignUp(false);
                app.switchToPayAnnualFee();
            }
        });
        refundPanel.add(renewButton);
        // Logout and Back Buttons (These should be placed at the bottom)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Aligns buttons to the left
        bottomPanel.setBackground(Yellow);

        backButton = new JButton("Back");
        backButton.setForeground(Red);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.switchToInitial(); // Switch to the initial screen
            }
        });

        logoutButton = new JButton("Log out");
        logoutButton.setForeground(Red);
        //logoutButton.setBackground(Yellow);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Logging out");
                app.switchToInitial();
                app.setCurrentUser(0);
                app.setRU(null);
                app.switchToInitial();
            }
        });

        bottomPanel.add(backButton);
        bottomPanel.add(logoutButton);

        // Add bottom panel to the main panel
        this.add(bottomPanel);
    }

    public void updateButtons() {
        if (app.getCurrentUser() == 0) {
            logoutButton.setVisible(false);
            backButton.setVisible(true);
            renewButton.setVisible(false);
        } else {
            logoutButton.setVisible(true);
            backButton.setVisible(false);
            renewButton.setVisible(true);
        }
    }
}
