package Repository;

import Entity.Payment;

import java.io.*;
import java.util.Formatter;

public class BuyProductService {
    private final File productFile = new File("src/main/resources/PRODUCT_LIST.txt");
    private final File paymentFile = new File("src/main/resources/PAYMENT_LIST.txt");

    private void inventory(Payment p) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(productFile));
        String line;
        StringBuilder formatData = new StringBuilder();
        while ((line = br.readLine()) != null) {
            String[] lines = line.split(",");
            if (p.getProductId() == Integer.parseInt(lines[0])) {
                int newStock = Integer.parseInt(lines[3]) - p.getProductQty();
                if (newStock != 0) {
                    formatData.append(lines[0]).append(",").append(lines[1]).append(",").append(lines[2]).append(",").append(newStock).append("\n");
                }
            } else {
                formatData.append(line).append("\n");
            }
        }

        Formatter formatter = new Formatter(productFile);
        formatter.format("%S", formatData);
        br.close();
        formatter.close();
    }

    public boolean doPayment(Payment p) {
        try {
            if (!paymentFile.exists()) paymentFile.createNewFile();
            if (!productFile.exists()) productFile.createNewFile();
            inventory(p);
            BufferedWriter br = new BufferedWriter(new FileWriter(paymentFile, true));
            br.write(p.getProductId() + "," + p.getProductBrandName() + "," + p.getProductPrice() + "," + p.getProductQty() + "\n");
            br.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
