package Repository;

import Entity.Product;

import java.io.*;
import java.util.Arrays;
import java.util.Formatter;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddProductService {

    private final File productFile = new File("src/main/resources/PRODUCT_LIST.txt");


    public boolean isProductValid(Product product) {
        boolean productDuplicate = false;
        try {
            if (!productFile.exists()) productFile.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(productFile));
            String line;
            StringBuilder formatData = new StringBuilder();
            while ((line = br.readLine()) != null) {
                String[] lines = line.split(",");
                int id = Integer.parseInt(lines[0]);
                double price = Double.parseDouble(lines[2]);
                if (id == product.getProductId()) {
                    if (price == product.getProductPrice() && product.getProductBrandName().equalsIgnoreCase(lines[1])) {
                        formatData.append(lines[0]).append(",")
                                .append(lines[1]).append(",")
                                .append(lines[2]).append(",")
                                .append(Integer.parseInt(lines[3]) + product.getProductQty()).append("\n");
                    } else {
                        formatData.append(line).append("\n");
                        return false;
                    }
                    productDuplicate = true;
                } else {
                    formatData.append(line).append("\n");
                }
            }

            if (!productDuplicate) {
                formatData.append(product.getProductId())
                        .append(",").append(product.getProductBrandName())
                        .append(",").append(product.getProductPrice())
                        .append(",").append(product.getProductQty()).append("\n");
            }


            Formatter formatter = new Formatter(productFile);
            formatter.format("%S", formatData);

            br.close();
            formatter.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
