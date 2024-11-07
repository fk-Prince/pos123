package Product.ProductRepository;

import Product.Entity.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static Product.ProductRepository.AddProductRepository.productFile;

public class InventoryRepository {

    public static List<Product> getAllProducts() throws IOException {
        if (!productFile.exists()) productFile.createNewFile();
        return new BufferedReader(new FileReader(productFile))
                .lines().map(lines -> lines.split(","))
                .map(lines -> new Product(Integer.parseInt(lines[0]), lines[1], Double.parseDouble(lines[2]), Integer.parseInt(lines[3])))
                .toList();
    }
}
