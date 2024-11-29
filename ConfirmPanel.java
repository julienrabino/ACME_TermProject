import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmPanel extends JPanel {
    private Color Red = new Color(139, 0, 0);
    private Color Yellow = new Color(255, 248, 191);
    private Color Orange = new Color(244, 138, 104);
    public ConfirmPanel(MovieTheatreApp app, UserDatabaseManager userDBM, MovieTheatreController movieTC) {
        this.setLayout(new BorderLayout());
        this.setBackground(Yellow);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setForeground(Red);
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                // SHOULD SWITCH TO PAYMENT PANEL
                app.switchToMovieList();
            }
        });

        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centering the button
        confirmPanel.add(confirmButton);
        confirmPanel.setBackground(Yellow);
        this.add(confirmPanel);

        this.add(Box.createVerticalStrut(20));  // Adds vertical space between buttons

        JButton backButton = new JButton("Back");
        backButton.setForeground(Red);
        confirmPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.switchToMovieList();
            }
        });

        this.add(confirmPanel, BorderLayout.SOUTH);

    }
}
