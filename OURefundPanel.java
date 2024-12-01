import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OURefundPanel extends JPanel {
    private int width = 100;
    private int height = 30;
    private JTextField usrInput;
    private JPasswordField pwInput;
    private String username;
    private String password;

    private MovieTheatreApp app;
    private Color Red = new Color(139, 0, 0);
    private Color Yellow = new Color(255, 248, 191);
    private Color Orange = new Color(244, 138, 104);

    public OURefundPanel(MovieTheatreApp app,UserDatabaseManager userDBM) {
        this.app = app;

        JLabel usrLabel = new JLabel("Email:");
        usrLabel.setForeground(Red);

        usrInput = new JTextField(15);
        usrInput.setPreferredSize(new Dimension(width, height));

        JButton submitButton = new JButton("Submit");
        submitButton.setForeground(Red);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RURefundPanel refund = app.getRURefundPanel();
                refund.displayPurchasedTickets(usrInput.getText());
                app.switchToRURefundPanel();
            }
        });
        JButton backButton = new JButton("Back");
        backButton.setForeground(Red);
        backButton.addActionListener(e -> {
            System.out.println("Going back to guest panel");
            app.switchToGuest();
        });


        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        JLabel title = new JLabel("Enter email used for ticket purchasing");
        title.setForeground(Red);
        headerPanel.add(title);
        headerPanel.setBackground(Yellow);

        this.add(headerPanel, BorderLayout.NORTH);

        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        submitPanel.add(backButton);
        submitPanel.add(submitButton);
        this.add(submitPanel, BorderLayout.PAGE_END);
        submitPanel.setBackground(Orange);

        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        clientPanel.setBackground(Yellow);

        int row = 0;
        addComponent(clientPanel, usrLabel, 0, row, gbc);
        addComponent(clientPanel, usrInput, 1, row++, gbc);

        this.add(clientPanel, BorderLayout.CENTER);

    }
    private void addComponent(JPanel panel, Component component, int gridx, int gridy, GridBagConstraints gbc) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        panel.add(component, gbc);
    }

    private boolean validateEmail(UserDatabaseManager userDBM) {
        boolean valid = true;

        return valid;
    }

}