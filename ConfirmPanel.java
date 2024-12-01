
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmPanel extends JPanel {
    private Color Red = new Color(139, 0, 0);
    private Color Yellow = new Color(255, 248, 191);
    private Color Orange = new Color(244, 138, 104);
    private MovieTheatreApp app;
    private Movie movie;
    private Location theatre;
    private Showtime showtime;
    private Seat seat;
    private JLabel movieName;
    private JLabel theatreName;
    private JLabel seatName;
    private JLabel showtimeName;
    private JLabel costName;
    private JPanel clientPanel;
    private GridBagConstraints gbc;

    public ConfirmPanel(MovieTheatreApp app, UserDatabaseManager userDBM, MovieTheatreController movieTC) {

        this.app = app;

        this.setLayout(new BorderLayout());
        this.setBackground(Yellow);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setForeground(Red);
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                // SHOULD SWITCH TO PAYMENT PANEL
                if (app.getCurrentUser() == 1) {// if a RU is currently using our app
                    PaymentRUPanel pay = app.getPaymentRUPanel();
                    pay.displaySavedPayments(app.getRU());
                    app.switchToPaymentRU();
                }
                else if (app.getCurrentUser() == 0){ // if its a guest currently using it
                    app.switchToPayment();
                }
            }
        });

        JPanel centerPanel = new JPanel();
        System.out.println("IN CONFIRM PANEL");
        Movie movie = app.getMovie();  // Assuming app.getMovie() returns a Movie object
        Location theatre = app.getTheatre();  // Assuming app.getTheatre() returns a Theatre object


        clientPanel = new JPanel();
        clientPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        clientPanel.setBackground(Yellow);


        this.add(clientPanel, BorderLayout.CENTER);


        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centering the button
        confirmPanel.setBackground(Yellow);

        this.add(Box.createVerticalStrut(20));  // Adds vertical space between buttons

        JButton backButton = new JButton("Back");
        backButton.setForeground(Red);
        confirmPanel.add(backButton);
        confirmPanel.add(confirmButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movieName.setText("");
                theatreName.setText("");
                showtimeName.setText("");
                seatName.setText("");
                int row = 0;
                addComponent(clientPanel, movieName, 1, row++, gbc);
                addComponent(clientPanel, theatreName, 1, row++, gbc);
                addComponent(clientPanel, seatName, 1, row++, gbc);
                addComponent(clientPanel, showtimeName, 1, row++, gbc);
                app.switchToMovieList();
            }
        });

        this.add(confirmPanel, BorderLayout.SOUTH);

    }

    public void getInfo() {
        clientPanel.removeAll();
        this.seat = app.getSelectedSeat();
        this.movie = app.getMovie();
        this.theatre = app.getTheatre();
        this.showtime = app.getSelectedShowtime();

        System.out.println("IN GET INFO");
        System.out.println(movie.getTitle());
        System.out.println(theatre.getName());

        movieName = new JLabel(movie.getTitle());
        movieName.setForeground(Red);
        theatreName = new JLabel(theatre.getName());
        theatreName.setForeground(Red);
        seatName = new JLabel(seat.toString());
        seatName.setForeground(Red);
        String showtimeStr = showtime.getDate() + " @ " + showtime.getTime();
        showtimeName = new JLabel(showtimeStr);
        showtimeName.setForeground(Red);
        costName = new JLabel("$12.50");
        costName.setForeground(Red);
        //seatName = new JLabel(app.getSeat());
        JLabel movieLabel = new JLabel("Movie: ");
        movieLabel.setForeground(Red);
        JLabel theatreLabel = new JLabel("Theatre: ");
        theatreLabel.setForeground(Red);
        JLabel seatLabel = new JLabel("Seat: ");
        seatLabel.setForeground(Red);
        JLabel showtimeLabel = new JLabel("Showtime: ");
        showtimeLabel.setForeground(Red);
        JLabel costLabel = new JLabel("Cost: ");
        costLabel.setForeground(Red);

        int row = 0;
        addComponent(clientPanel, movieLabel, 0, row, gbc);
        addComponent(clientPanel, movieName, 1, row++, gbc);

        addComponent(clientPanel, theatreLabel, 0, row, gbc);
        addComponent(clientPanel, theatreName, 1, row++, gbc);

        addComponent(clientPanel, seatLabel, 0, row, gbc);
        addComponent(clientPanel, seatName, 1, row++, gbc);

        addComponent(clientPanel, showtimeLabel, 0, row, gbc);
        addComponent(clientPanel, showtimeName, 1, row++, gbc);

        addComponent(clientPanel, costLabel, 0, row, gbc);
        addComponent(clientPanel, showtimeName, 1, row++, gbc);

        this.add(clientPanel, BorderLayout.CENTER);
    }

    private void addComponent(JPanel panel, Component component, int gridx, int gridy, GridBagConstraints gbc) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        panel.add(component, gbc);
    }
}

