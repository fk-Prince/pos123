package Frame.PointOfSalePanels;

import Frame.Interfaces.PanelSwitching;
import Frame.Interfaces.TransitionPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame implements PanelSwitching, TransitionPanel {

    private CardLayout cardLayout;
    private JPanel cardPanel, mainPanel;
    private MigLayout migLayout;

    private ItemListTable itemList;
    private RegisterItem registerItem;
    private BuyItem buyItem;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

    public MainFrame() {
        init();
        initComponent();
        setVisible(true);
    }

    private void initComponent() {
        migLayout = new MigLayout("fill, insets 0 0 0 0");
        mainPanel = new JPanel(migLayout);
        mainPanel.setBackground(Color.WHITE);

        cardPanel = new JPanel(new CardLayout());
        cardPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        cardLayout = (CardLayout) cardPanel.getLayout();


        itemList = new ItemListTable();                  //TABLE
        SideBar sideBar = new SideBar();                 //SIDEBAR
        registerItem = new RegisterItem();               //REGISTER ITEM
        buyItem = new BuyItem();                         //BUY ITEM

        // ADD LISTENERS
        itemList.setListener(registerItem);
        itemList.setListener(buyItem);
        buyItem.setListener(itemList);
        sideBar.setListeners(this, this, itemList);
        registerItem.setListener(itemList);

        //MAIN PANELS
        mainPanel.add(sideBar, "w 50!, pos 0 0, h 100%");
        mainPanel.add(itemList, "w 56%, pos 4% 0, h 100%");
        mainPanel.add(cardPanel, "w 40%!, pos 60% 0, h 100%");

        //CARD PANELS
        cardPanel.add(registerItem, "REGISTER_ITEM");
        cardPanel.add(buyItem, "BUY_ITEM");

        //SHOW INITIAL PANEL
        cardLayout.show(cardPanel, "BUY_ITEM");
        add(mainPanel);
    }

    private void init() {
        String TITLE = "Point Of Sale";
        int WIDTH = 1300;
        int HEIGHT = 700;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void switchPanelTo(String panelName) {
        itemList.refreshTableData();

        if (panelName.equals("REGISTER_ITEM")) {
            itemList.setListener(registerItem);
            ((RegisterItem) cardPanel.getComponent(0)).clearFields();
        }
        if (panelName.equals("BUY_ITEM")) {
            itemList.setListener(buyItem);
            ((BuyItem) cardPanel.getComponent(1)).clearFields();
        }

        cardLayout.show(cardPanel, panelName);
    }

    @Override
    public void animate() {

    }



    @Override
    public void animatePanel(JPanel panelName, String panelPosition) {
        migLayout.setComponentConstraints(panelName, panelPosition);
        mainPanel.revalidate();
    }


}
