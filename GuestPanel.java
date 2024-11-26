import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuestPanel extends JPanel {
    public GuestPanel(MovieTheatreApp app, MovieTheatreController movieTC){

        JButton browseMovies = new JButton("Browse Movies");
        browseMovies.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.switchToMovieList();
            }
        });

        this.setLayout(new FlowLayout());
        this.add(browseMovies);


    }

}
