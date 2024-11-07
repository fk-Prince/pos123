package Frame.PointOfSalePanels;

import Entity.GenerateSales;
import Entity.Payment;
import Frame.Interfaces.TableRefresh;
import Frame.SwingComponents.MessageDialog;
import Frame.SwingComponents.MyButton;
import Repository.BuyProductService;
import Repository.TotalSalesService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Queue;
import java.util.List;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class SalesPanel extends JPanel {

    private static JFrame frame;
    private Queue<Payment> cartProduct;
    private final String title;
    private final boolean isCart;
    private TableRefresh refreshTable;
    private List<Payment> paymentList;

    public SalesPanel(Queue<Payment> cartProduct, TableRefresh refreshTable, String title, boolean isCart) {
        this.isCart = isCart;
        this.title = title;
        this.cartProduct = cartProduct;
        this.refreshTable = refreshTable;
        init();
        initComponents();
    }

    public SalesPanel(String title, boolean isCart) {
        this.title = title;
        this.isCart = isCart;
        paymentList = new TotalSalesService().getOverallSales();
        init();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(displayHeader(), BorderLayout.NORTH);
        add(displayItems(), BorderLayout.CENTER);
        add(displayTotal(), BorderLayout.SOUTH);

        frame.add(this);
        frame.setVisible(true);
    }

    private JScrollPane displayItems() {
        JPanel itemsPanel = new JPanel(new MigLayout("fillx,insets 0"));
        int row = 0;
        row++;

        if (isCart) {
            new CartProduct(row, itemsPanel, cartProduct);
        } else {
            new DisplaySales(row, itemsPanel, paymentList);
        }

        JScrollPane scroll = new JScrollPane(itemsPanel);
        scroll.setBorder(new EmptyBorder(0, 20, 0, 0));
        JScrollBar vscrollBar = scroll.getVerticalScrollBar();
        vscrollBar.setBlockIncrement(15);
        vscrollBar.setUnitIncrement(15);

        return scroll;
    }


    private JPanel displayHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(1, 100));
        headerPanel.setBackground(Color.LIGHT_GRAY);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel label = new JLabel(title, JLabel.CENTER);
        label.setFont(new Font("Sans serif", Font.BOLD | Font.ITALIC, 23));
        headerPanel.add(label, BorderLayout.CENTER);

        JPanel productHeader = getProductHeader();
        headerPanel.add(productHeader, BorderLayout.SOUTH);
        return headerPanel;
    }


    private JPanel displayTotal() {
        JPanel receiptTotal = new JPanel(new BorderLayout());
        receiptTotal.setBorder(new EmptyBorder(10, 10, 10, 10));


        JPanel buttonHolder = new JPanel(new BorderLayout());
        if (isCart) {
            double total = cartProduct.stream().mapToDouble(Payment::compute).sum();
            JLabel totalLabel = new JLabel("Total: " + String.format("%.2f", total));
            totalLabel.setFont(new Font("Sans serif", Font.BOLD, 16));
            receiptTotal.add(totalLabel, BorderLayout.EAST);

            MyButton clearButton = new MyButton("Clear", false);
            clearButton.setPreferredSize(new Dimension(100, 40));
            clearButton.addActionListener(e -> {
                cartProduct.clear();
                refreshTable.refreshTableData();
                frame.dispose();
            });
            buttonHolder.add(payButton(), BorderLayout.WEST);
            buttonHolder.add(clearButton, BorderLayout.CENTER);
        } else {
            JLabel totalLabel = new JLabel("Generated Sales Total: " + String.format("%.2f", new GenerateSales().computeSales()));
            totalLabel.setFont(new Font("Sans serif", Font.BOLD, 16));
            receiptTotal.add(totalLabel, BorderLayout.EAST);
        }

        MyButton backButton = new MyButton("Back", false);
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.addActionListener(e -> frame.dispose());
        buttonHolder.add(backButton, BorderLayout.EAST);
        receiptTotal.add(buttonHolder, BorderLayout.WEST);

        return receiptTotal;
    }

    public JButton payButton() {
        MyButton payButton = new MyButton("Pay", true);
        payButton.addActionListener(e -> {
            boolean success = false;
            for (Payment pay : cartProduct) {
                success = new BuyProductService().doPayment(pay);
            }
            if (success) {
                JOptionPane.showMessageDialog(this, "PURCHASED SUCCESSFULLY");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No items in the cart");
            }
            cartProduct.clear();
            refreshTable.refreshTableData();
        });
        payButton.setPreferredSize(new Dimension(100, 40));
        return payButton;
    }

    private JPanel getProductHeader() {
        int col = 0;
        int row = 0;
        JPanel productHeader = new JPanel(new MigLayout("fillx,insets 0"));
        String[] header = {"Product Id", "Product Name", "Price", "Qty", "Total(TAX 12%)"};
        for (String h : header) {
            JLabel head = new JLabel(h);
            head.setFont(new Font("Arial", Font.PLAIN, 14));
            if (h.equals("Price") || h.equals("Qty")) {
                productHeader.add(head, "cell " + col + " " + row + ",al right");
            } else if (h.equals("Total(TAX 12%)")) {
                productHeader.add(head, "cell " + col + " " + row + ",al right,span 2");
            } else {
                productHeader.add(head, "cell " + col + " " + row + ",al left");
            }
            col++;
        }
        return productHeader;
    }

    private void init() {
        if (frame != null) frame.dispose();
        frame = new JFrame();
        frame.setSize(600, 650);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setUndecorated(true);

    }
}

