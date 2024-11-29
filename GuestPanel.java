import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuestPanel extends JPanel {
    private Color Red = new Color(139, 0, 0);
    private Color Yellow = new Color(255, 248, 191);
    private Color Orange = new Color(244, 138, 104);
    public GuestPanel(MovieTheatreApp app, MovieTheatreController movieTC) {
        this.setLayout(new GridLayout(3, 3, 10, 10));
        this.setBackground(Yellow);

        JButton browseMovies = new JButton("Browse Movies");
        browseMovies.setForeground(Red);
        browseMovies.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.switchToMovieList();
            }
        });

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centering the button
        centerPanel.add(browseMovies);
        centerPanel.setBackground(Yellow);
        this.add(centerPanel);

        this.add(Box.createVerticalStrut(20));  // Adds vertical space between buttons

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Aligns the back button to the left
        JButton backButton = new JButton("Back");
        backButton.setForeground(Red);
        bottomPanel.setBackground(Yellow);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.switchToInitial(); // Switch to the initial screen
            }
        });

        bottomPanel.add(backButton);

        this.add(centerPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

    }
}
