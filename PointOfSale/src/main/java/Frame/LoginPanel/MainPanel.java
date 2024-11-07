package Frame.LoginPanel;

import Frame.Interfaces.PanelSwitching;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JFrame implements PanelSwitching {

    private JPanel mainPanel;
    private MigLayout migLayout;
    private boolean isLogin = true;
    private Timer timer;
    private int POSITION_LOGIN_X = 0;
    private int POSITION_SIGNUP_X = 100;

    private Login login;
    private Signup signup;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainPanel::new);
    }

    public MainPanel() {
        init();
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        migLayout = new MigLayout("fill,insets 0");
        mainPanel = new JPanel(migLayout);
        mainPanel.setBackground(Color.BLACK);

        login = new Login(this);
        signup = new Signup(this);
        mainPanel.add(login, "pos 0 0, w 100%, h 100%");
        mainPanel.add(signup, "pos 100% 0, w 100%, h 100%");
        add(mainPanel);
    }

    private void init() {
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
    }


    @Override
    public void switchPanelTo(String panel) {

    }

    @Override
    public void animate() {

        if (timer != null && timer.isRunning()) {
            return;
        }
        timer = new Timer(0, e -> {
            POSITION_LOGIN_X = isLogin ? POSITION_LOGIN_X - 5 : POSITION_LOGIN_X + 5;
            POSITION_SIGNUP_X = isLogin ? POSITION_SIGNUP_X - 5 : POSITION_SIGNUP_X + 5;

            migLayout.setComponentConstraints(login, "pos " + POSITION_LOGIN_X + "% 0, w 100%, h 100%!");
            migLayout.setComponentConstraints(signup, "pos " + POSITION_SIGNUP_X + "% 0, w 100%, h 100%!");

            if (POSITION_LOGIN_X == -100 || POSITION_LOGIN_X == 0) {
                isLogin = !isLogin;
                timer.stop();
            }

            mainPanel.revalidate();
        });
        timer.start();
    }
}
