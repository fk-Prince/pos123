package Product.ProductRepository;

import Product.Entity.Payment;
import Product.Entity.Product;

import java.io.*;
import java.util.List;

public class AddProductRepository {
    protected final static File productFile = new File("Product.txt");
    final static File paymentFile = new File("Payment.txt");


    public static void addProduct(Product productToAdd) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(productFile));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            String[] lines = line.split(",");
            if (Integer.parseInt(lines[0]) == productToAdd.getProductId()) {
                int newQty = Integer.parseInt(lines[3]) + productToAdd.getProductQty();
                sb.append(productToAdd.getProductId()).append(",")
                        .append(productToAdd.getProductName()).append(",")
                        .append(productToAdd.getProductPrice()).append(",")
                        .append(newQty).append("\n");
            } else {
                sb.append(line).append("\n");
            }
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(productFile));
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    public static void addStaticProduct(List<Product> products) {
        try {
            if (!productFile.exists()) productFile.createNewFile();
            if (productFile.length() != 0) return;
            BufferedWriter bw = new BufferedWriter(new FileWriter(productFile, true));
            for (Product product : products) {
                bw.write(product.getProductId() + "," + product.getProductName() + "," + product.getProductPrice() + "," + product.getProductQty() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
