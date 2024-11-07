package Frame.PointOfSalePanels;

import Frame.Interfaces.PanelSwitching;
import Frame.Interfaces.TableRefresh;
import Frame.Interfaces.TransitionPanel;
import Frame.SwingComponents.MySideBarButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SideBar extends JPanel {

    protected boolean isSideBarActive;
    private Timer timer;
    private int SIDEBAR_POSITION = 50;

    private final List<MySideBarButton> buttonlist;
    private PanelSwitching panelSwitching;
    private TransitionPanel transitionPanel;
    private TableRefresh table;
    private JButton toggleSideBarButton;

    public SideBar() {
        buttonlist = new ArrayList<>();
        init();
        setOpaque(true);
    }

    public void init() {
        setLayout(new MigLayout("fillx,insets 0 0 0 0", "[]0[]", "[]2[]"));
        setBackground(new Color(92, 110, 129));

        MySideBarButton b1 = new MySideBarButton("BUY PRODUCT", "/Image/buy.png");
        MySideBarButton b2 = new MySideBarButton("REGISTER PRODUCT", "/Image/buy.png");
        MySideBarButton b3 = new MySideBarButton("GENERATE SALES", "/Image/buy.png");

        add(addSlideButton(), "ay 20%, ax 95%, wrap");
        add(b1, "wrap,gaptop 15%");
        add(b2, "wrap");
        add(b3, "wrap");

        b1.addActionListener(e -> {
            panelSwitching.switchPanelTo("BUY_ITEM");
            if (isSideBarActive) animationSideBar();
        });
        b2.addActionListener(e -> {
            panelSwitching.switchPanelTo("REGISTER_ITEM");
            if (isSideBarActive) animationSideBar();
        });
        b3.addActionListener(e -> new SalesPanel("Generated Sales", false));


        buttonlist.add(b1);
        buttonlist.add(b2);
        buttonlist.add(b3);
    }

    private JButton addSlideButton() {
        toggleSideBarButton = new JButton() {
            private final Image hide = new ImageIcon(getClass().getResource("/Image/exit.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            private final Image show = new ImageIcon(getClass().getResource("/Image/show.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                Image image = isSideBarActive ? hide : show;
                if (isSideBarActive) {
                    g2d.drawImage(image, getWidth() - image.getWidth(null) + 10, (getHeight() - image.getHeight(null)) / 2, null);
                } else {
                    g2d.drawImage(image, getWidth() - image.getWidth(null) + 4, (getHeight() - image.getHeight(null)) / 2, null);
                }
                repaint();
                g2d.dispose();

            }
        };
        toggleSideBarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleSideBarButton.setPreferredSize(new Dimension(0, 50));
        toggleSideBarButton.setContentAreaFilled(false);
        toggleSideBarButton.setBorderPainted(false);
        toggleSideBarButton.setFocusPainted(false);
        toggleSideBarButton.setOpaque(false);
        toggleSideBarButton.addActionListener(e -> animationSideBar());

        return toggleSideBarButton;
    }

    private void animationSideBar() {
        toggleSideBarButton.setEnabled(false);
        table.setEnabled(false);
        buttonlist.forEach(e -> e.setEnabled(false));


        timer = new Timer(0, evt -> {
            SIDEBAR_POSITION = isSideBarActive ? SIDEBAR_POSITION - 25 : SIDEBAR_POSITION + 25;
            transitionPanel.animatePanel(this, "pos 0 0, h 100%, w " + SIDEBAR_POSITION + "!");

            if (SIDEBAR_POSITION >= 250 || SIDEBAR_POSITION <= 50) {
                isSideBarActive = !isSideBarActive;
                table.setEnabled(!isSideBarActive);
                timer.stop();
                buttonlist.forEach(e -> e.setActive(isSideBarActive));
                toggleSideBarButton.setEnabled(true);
            }

            if (SIDEBAR_POSITION == 100) buttonlist.forEach(e -> e.setActive(true));

            revalidate();
        });
        timer.setDelay(0);
        timer.start();
    }

    public void setListeners(PanelSwitching panelSwitching, TransitionPanel transitionPanel, TableRefresh table) {
        this.panelSwitching = panelSwitching;
        this.table = table;
        this.transitionPanel = transitionPanel;
    }

}
