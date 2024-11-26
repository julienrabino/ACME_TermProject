import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private int width = 100;
    private int height = 30;
    private JTextField usrInput;
    private JPasswordField pwInput;

    private MovieTheatreApp app;

    public LoginPanel(MovieTheatreApp app, myJDBC db) {
        this.app = app;

        JLabel usrLabel = new JLabel("Username:");
        JLabel pwLabel = new JLabel("Password:");

        usrInput = new JTextField(15);
        pwInput = new JPasswordField(15);
        usrInput.setPreferredSize(new Dimension(width, height));
        pwInput.setPreferredSize(new Dimension(width, height));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usrInput.getText();
                String password = new String(pwInput.getPassword());

                // Need to change/add to this stuff still
                if ("admin".equals(username) && "password".equals(password)) {
                    JOptionPane.showMessageDialog(app, "Logged in successfully!");
                    app.switchToMovieList();  // switch to the movie list panel
                } else {
                    JOptionPane.showMessageDialog(app, "Invalid username or password.");
                }
            }
        });

        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        JLabel title = new JLabel("Sign In");
        headerPanel.add(title);

        this.add(headerPanel, BorderLayout.NORTH);

        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        submitPanel.add(submitButton);
        this.add(submitPanel, BorderLayout.PAGE_END);

        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);


        int row = 0;
        addComponent(clientPanel, usrLabel, 0, row, gbc);
        addComponent(clientPanel, usrInput, 1, row++, gbc);

        addComponent(clientPanel, pwLabel, 0, row, gbc);
        addComponent(clientPanel, pwInput, 1, row++, gbc);

        this.add(clientPanel, BorderLayout.CENTER);

    }
    private void addComponent(JPanel panel, Component component, int gridx, int gridy, GridBagConstraints gbc) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        panel.add(component, gbc);
    }

}