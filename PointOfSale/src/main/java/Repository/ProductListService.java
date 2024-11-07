package Repository;

import Entity.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductListService {

    private final File productFile = new File("src/main/resources/PRODUCT_LIST.txt");

    public ProductListService() {

    }

    public List<Product> getProductList() {
        try {
            return new BufferedReader(new FileReader(productFile)).lines().map(lines -> lines.split(","))
                    .map(lines -> new Product(Integer.parseInt(lines[0]), lines[1], Double.parseDouble(lines[2]), Integer.parseInt(lines[3])))
                    .toList()
                    .stream()
                    .sorted(Comparator.comparingInt(Product::getProductId))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Product getProductList(int productId) {
        try {
            return new BufferedReader(new FileReader(productFile)).lines()
                    .map(lines -> lines.split(","))
                    .filter(line -> productId == Integer.parseInt(line[0]))
                    .map(line -> new Product(Integer.parseInt(line[0]), line[1], Double.parseDouble(line[2]), Integer.parseInt(line[3])))
                    .findFirst().orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
//        try {
//            if (!productFile.exists()) productFile.createNewFile();
//            BufferedReader br = new BufferedReader(new FileReader(productFile));
//            String line;
//
//            while ((line = br.readLine()) != null) {
//                String[] lines = line.split(",");
//                if (productId == Integer.parseInt(lines[0])) {
//                    return new Product(Integer.parseInt(lines[0]), lines[1], Double.parseDouble(lines[2]), Integer.parseInt(lines[3]));
//                }
//            }
//            br.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
    }
}
