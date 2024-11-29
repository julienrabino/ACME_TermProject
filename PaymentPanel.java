import javax.swing.*;
import java.awt.*;

public class PaymentPanel extends JPanel {
    private TicketController ticketC;
    private MovieTheatreApp app;
    private Color Red = new Color(139, 0, 0);
    private Color Yellow = new Color(255, 248, 191);
    private Color Orange = new Color(244, 138, 104);
    private int bWidth = 100;
    private int bHeight = 50;

    private JTextField usrInput;
    private JPasswordField pwInput;
    private JPasswordField pwConfirmInput;
    private JTextField fnameInput;
    private JTextField lnameInput;
    private JTextField emailInput;
    private JTextField addressInput;


    public PaymentPanel(MovieTheatreApp app, UserDatabaseManager userDBM, TicketController ticketC) {
        this.ticketC = ticketC;
        this.app = app;
        this.setLayout(new BorderLayout());
        this.setBackground(Yellow);

    }

}
