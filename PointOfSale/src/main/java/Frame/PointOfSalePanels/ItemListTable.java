package Frame.PointOfSalePanels;

import Frame.Interfaces.ProductSelected;
import Frame.Interfaces.TableRefresh;
import Entity.Product;
import Frame.SwingComponents.MessageDialog;
import Repository.ProductListService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ItemListTable extends JPanel implements TableRefresh {

    private JTable table;
    private DefaultTableModel model;
    private final ProductListService productList;
    private ProductSelected productSelected;
    private List<Product> listOfItems;


    public ItemListTable() {
        productList = new ProductListService();
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.RED);
        setOpaque(false);
        displayData();
        title();
    }

    private void title() {
        JLabel label = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                FontMetrics fm = g2d.getFontMetrics();
                String title = "Point Of Sale";
                g2d.drawString(title, (getWidth() - fm.stringWidth(title)) / 2, 50);

                g2d.dispose();
            }
        };
        label.setFocusable(true);
        label.setFont(new Font("Arial", Font.ITALIC, 30));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(1, 100));
        add(label, BorderLayout.NORTH);
    }

    private void displayData() {
        model = new DefaultTableModel();
        model.addColumn("PRODUCT ID");
        model.addColumn("PRODUCT NAME");
        model.addColumn("PRODUCT PRICE");
        model.addColumn("PRODUCT STOCK");

        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(SwingConstants.CENTER);
                renderer.setVerticalAlignment(SwingConstants.CENTER);
                return renderer;
            }

        };
        table.setRowHeight(30);
        table.setBackground(Color.DARK_GRAY);
        table.setForeground(Color.WHITE);
        table.setGridColor(Color.WHITE);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int item = (int) model.getValueAt(row, 0);
                    productSelected.setProduct(productList.getProductList(item));
                }
            }
        });
        TableColumn col0 = table.getColumnModel().getColumn(0);
        col0.setPreferredWidth(5);


        JTableHeader header = table.getTableHeader();
        header.setEnabled(false);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setFont(new Font("Arial", Font.BOLD, 15));
        header.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));


        refreshTableData();

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(Color.DARK_GRAY);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);
    }

    @Override
    public void setEnabled(boolean enabled) {
        table.setEnabled(enabled);
    }

    @Override
    public boolean isPaymentValid(Product product) {
        for (Product p : listOfItems) {
            if (product.getProductId() == p.getProductId()) {
                int stock = p.getProductQty() - product.getProductQty();
                if (product.getProductQty() > p.getProductQty()) {
                    return false;
                }

                listOfItems.remove(p);
                if (stock > 0) {
                    listOfItems.add(new Product(p.getProductId(), p.getProductBrandName(), p.getProductPrice(), stock));
                }
                listOfItems.sort(Comparator.comparingInt(Product::getProductId));
                break;
            }
        }
        return true;
    }

    @Override
    public void refreshTable() {
        model.setRowCount(0);
        for (Product product : listOfItems) {
            model.addRow(new Object[]{product.getProductId(), product.getProductBrandName(), product.getProductPrice(), product.getProductQty()});
        }
    }

    @Override
    public void refreshTableData() {
        model.setRowCount(0);
        listOfItems = productList.getProductList();
        for (Product product : listOfItems) {
            model.addRow(new Object[]{product.getProductId(), product.getProductBrandName(), product.getProductPrice(), product.getProductQty()});
        }
    }


    public void setListener(ProductSelected productSelected) {
        this.productSelected = productSelected;
    }
}
