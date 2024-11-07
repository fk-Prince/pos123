package Frame.PointOfSalePanels;

import Entity.Payment;

import javax.swing.*;
import java.util.Queue;

public class CartProduct {
    public CartProduct(int row, JPanel itemsPanel, Queue<Payment> cartProduct) {
        for (Payment pay : cartProduct) {
            int col = 0;
            itemsPanel.add(new JLabel(String.valueOf(pay.getProductId())), "cell " + col + " " + row);
            col++;
            itemsPanel.add(new JLabel(pay.getProductBrandName()), "cell " + col + " " + row);
            col++;
            itemsPanel.add(new JLabel(String.format("%.2f", pay.getProductPrice())), "cell " + col + " " + row + ",al right");
            col++;
            itemsPanel.add(new JLabel(String.valueOf(pay.getProductQty())), "cell " + col + " " + row + ",al right");
            col++;
            itemsPanel.add(new JLabel(String.format("%.2f", pay.compute())), "cell " + col + " " + row + ",al right");
            row++;
        }
    }
}
