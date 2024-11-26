import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuestPanel extends JPanel {
    public GuestPanel(MovieTheatreApp app, MovieTheatreController movieTC) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton browseMovies = new JButton("Browse Movies");
        browseMovies.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.switchToMovieList();
            }
        });

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centering the button
        centerPanel.add(browseMovies);
        this.add(centerPanel);

        this.add(Box.createVerticalStrut(20));  // Adds vertical space between buttons

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Aligns the back button to the left
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.switchToInitial(); // Switch to the initial screen
            }
        });

        bottomPanel.add(backButton);

        this.add(Box.createVerticalGlue());
        this.add(bottomPanel);

    }
}
