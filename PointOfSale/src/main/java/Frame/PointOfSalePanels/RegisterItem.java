package Frame.PointOfSalePanels;

import Entity.Product;
import Frame.Interfaces.ProductSelected;
import Frame.Interfaces.TableRefresh;
import Frame.LoginPanel.AnimateMessage;
import Repository.AddProductService;
import Frame.SwingComponents.MyButton;
import Frame.SwingComponents.MyTextField;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class RegisterItem extends JPanel implements ProductSelected {

    private TableRefresh refreshTable;
    private MyTextField productName, productPrice, productQty, productId;
    private MigLayout migLayout;

    public RegisterItem() {
        migLayout = new MigLayout("fill, insets 0 0 0 0", "[0]0[0]", "[0]0[0]");
        setLayout(migLayout);

        init();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(223, 217, 217));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString("REGISTER ITEM", (getWidth() - fm.stringWidth("REGISTER ITEM")) / 2, 50);


        g2d.dispose();
    }

    private void init() {
        productId = new MyTextField("ITEM ID. . .", true);
        productName = new MyTextField("ITEM NAME. . .", true);
        productPrice = new MyTextField("ITEM PRICE. . .", true);
        productQty = new MyTextField("ITEM QUANTITY. . .", true);

        MyButton register = new MyButton("ADD", true);
        register.setPreferredSize(new Dimension(260, 70));
        register.addActionListener(e -> {
            if (productName.getStringText().isEmpty()) {
                AnimateMessage.showMessage("Please fill the inputs", true, migLayout, this, 25, 0);
                revalidate();
                return;
            }
            if (!productId.isIntValid() || !productPrice.isPriceValid() || !productQty.isIntValid() || productName.getStringText().isEmpty()) {
                AnimateMessage.showMessage("Invalid Input", true, migLayout, this, 25, 0);
                revalidate();
                return;
            }
            Product product = new Product(productId.getIntText(),
                    productName.getStringText(),
                    productPrice.getDoubleText(),
                    productQty.getIntText());

            AddProductService productService = new AddProductService();

            if (productService.isProductValid(product)) {
                AnimateMessage.showMessage("Product Added", false, migLayout, this, 25, 0);
            } else {
                AnimateMessage.showMessage("Product Id is already taken", true, migLayout, this, 25, 0);
            }

            revalidate();
            clearFields();
            refreshTable.refreshTableData();
        });

        add(productId, "pos 15% 15%, w 70%!,wrap");
        add(productName, "pos 15% 30%, w 70%!,wrap");
        add(productPrice, "pos 15% 45%, w 70%!,wrap");
        add(productQty, "pos 15% 60%, w 70%!,wrap");
        add(register, "pos 30% 80%, w 40%!,wrap");
    }


    public void clearFields() {
        productId.setText("");
        productName.setText("");
        productPrice.setText("");
        productQty.setText("");
    }

    public void setListener(TableRefresh refreshTable) {
        this.refreshTable = refreshTable;
    }

    @Override
    public void setProduct(Product product) {
        System.out.println("asdbasdbasdba");
        if (productId != null) {
            productId.setText(String.valueOf(product.getProductId()));
            productName.setText(product.getProductBrandName());
            productPrice.setText(String.valueOf(product.getProductPrice()));
            // productQty.setText(String.valueOf(product.getProductQty()));
        }
    }
}

