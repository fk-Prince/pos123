package Frame.LoginPanel;

import Entity.User;
import Frame.Interfaces.PanelSwitching;
import Frame.PointOfSalePanels.MainFrame;
import Frame.SwingComponents.TextButton;
import Frame.SwingComponents.TextField.FieldPanel;
import Frame.SwingComponents.MyButton;
import Frame.SwingComponents.PasswordField.PasswordPanel;
import Repository.UserRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class Login extends JPanel {
    private final PanelSwitching panelSwitching;
    private MigLayout migLayout;

    public Login(PanelSwitching panelSwitching) {
        this.panelSwitching = panelSwitching;
        migLayout = new MigLayout("fill,insets 0");
        setLayout(migLayout);
        initComponents();
    }

    private void initComponents() {
        JPanel fields = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(50, 10, 10, 10);
        gbc.fill = GridBagConstraints.CENTER;


        FieldPanel username = new FieldPanel("Username", "/Image/user.png");
        username.setPreferredSize(new Dimension(300, 50));
        gbc.gridy = 0;
        fields.add(username, gbc);
        gbc.insets = new Insets(10, 10, 10, 10);
        PasswordPanel password = new PasswordPanel("Password", "/Image/password.png");
        password.setPreferredSize(new Dimension(300, 50));
        gbc.gridy = 1;
        fields.add(password, gbc);

        MyButton button = new MyButton("Login", true);
        button.addActionListener(e -> {
            if (isInputsValid(username, password)) {
                username.setText("");//reset
                password.setText("");
            }
        });
        button.setPreferredSize(new Dimension(150, 50));
        gbc.gridy = 2;
        gbc.insets = new Insets(50, 10, 10, 10);
        fields.add(button, gbc);

        TextButton button1 = new TextButton("Don't have an account?", " Signup");
        button1.addActionListener(e -> {
            panelSwitching.animate();
            //reset
            username.setText("");
            password.setText("");
            username.isInputValid(true);
            password.isInputValid(true);
        });
        button1.setPreferredSize(new Dimension(180, 20));
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        fields.add(button1, gbc);
        add(fields, "al center");


        MyButton exitButton = new MyButton("Exit", false);
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setPreferredSize(new Dimension(100, 40));
        add(exitButton, "pos 85% 0");

    }

    private boolean isInputsValid(FieldPanel username, PasswordPanel password) {
        boolean isInputValid = true;
        if (username.getText().isEmpty()) {
            username.isInputValid(false);
            isInputValid = false;
        }
        if (password.getPassword().isEmpty()) {
            password.isInputValid(false);
            isInputValid = false;
        }
        if (!isInputValid) {
            AnimateMessage.showMessage("Please fill the input", true, migLayout, this, 32, 0);
            return false;
        }

        Optional<User> user = new UserRepository().checkAccount(new User(username.getText(), password.getPassword()));
        if (user.isPresent()) {
            AnimateMessage.showMessage("Successfully Logged in", false, migLayout, this, 32, 0);
            SwingUtilities.getWindowAncestor(this).dispose();
            SwingUtilities.invokeLater(() -> new MainFrame());
        } else {
            AnimateMessage.showMessage("Incorrect Password", true, migLayout, this, 32, 0);
        }

        //reset
        username.isInputValid(true);
        password.isInputValid(true);
        repaint();
        revalidate();
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.drawString("Login", (getWidth() - fm.stringWidth("Login")) / 2, 75);
        g2d.dispose();
    }


}
