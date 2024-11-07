package Frame.PointOfSalePanels;

import Entity.Payment;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DisplaySales {
    public DisplaySales(int row, JPanel itemsPanel, List<Payment> paymentList) {
        if (!paymentList.isEmpty()) {
            for (Payment pay : paymentList) {
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
        } else {
            JLabel label = new JLabel("No Sales Yet");
            label.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
            itemsPanel.add(label, "pos 35% 50%");
        }
    }
}
