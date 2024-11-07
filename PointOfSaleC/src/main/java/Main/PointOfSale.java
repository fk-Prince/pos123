package Main;

import Exceptions.IdNotFoundException;
import Exceptions.InvalidInputException;
import Exceptions.OutOfStockException;
import Product.Entity.GenerateSales;
import Product.Entity.Payment;
import Product.Entity.Product;
import Product.ProductList.Ballpen;
import Product.ProductList.Bondpaper;
import Product.ProductList.Crayons;
import Product.ProductList.Notebook;
import Product.ProductRepository.AddProductRepository;
import Product.ProductRepository.InventoryRepository;
import Product.ProductRepository.PaymentRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class PointOfSale {
    private List<Product> productList;
    private final Scanner sc;


    public PointOfSale() throws IOException {
        sc = new Scanner(System.in);
        productList = new ArrayList<Product>();
        productList.add(new Ballpen(1, "Ballpen", 10.5, 0));
        productList.add(new Notebook(2, "Notebook", 15, 0));
        productList.add(new Bondpaper(3, "Bondpaper", 2, 0));
        productList.add(new Crayons(4, "Crayons", 30, 0));
        AddProductRepository.addStaticProduct(productList);

        productList = InventoryRepository.getAllProducts();
    }


    public void run() {
        while (true) {

            try {
                System.out.println("-------------------------------");
                System.out.println("[1]Show Available Products\n[2]Buy Product\n[3]Add Product\n[4]Generate Sales\n[5]Exit");
                System.out.print("Choice: ");
                int choice = Integer.parseInt(sc.nextLine());
                System.out.println("-------------------------------");
                switch (choice) {
                    case 1 -> productList.forEach(Product::displayDetails);
                    case 2 -> buyProduct();
                    case 3 -> addProduct();
                    case 4 -> generateSales();
                    case 5 -> System.exit(0);
                    default -> System.out.println("Invalid Choice");
                }
            } catch (OutOfStockException | IdNotFoundException | InvalidInputException e) {
                System.out.println(e.getMessage());
            }


        }
    }

    private void generateSales() {
        GenerateSales generateSales = new GenerateSales();
        System.out.println("Total Overall Sales");
        System.out.println(generateSales.computeSales());
    }

    private void addProduct() throws IdNotFoundException, InvalidInputException {
        System.out.print("Enter Product Id to add Qty: ");
        int productIdA = Integer.parseInt(sc.nextLine());
        Product productToAdd = productList.stream()
                .filter(product -> productIdA == product.getProductId())
                .findFirst()
                .orElse(null);

        if (productToAdd == null) {
            throw new IdNotFoundException("Product Id doesn't exist.");
        }

        System.out.print("How many qty/s you want to add: ");
        int productQtyA = Integer.parseInt(sc.nextLine());
        if (productQtyA > 0) {
            switch (productToAdd.getProductId()) {
                case 1 ->
                        productToAdd = new Ballpen(productToAdd.getProductId(), productToAdd.getProductName(), productToAdd.getProductPrice(), productQtyA);
                case 2 ->
                        productToAdd = new Bondpaper(productToAdd.getProductId(), productToAdd.getProductName(), productToAdd.getProductPrice(), productQtyA);
                case 3 ->
                        productToAdd = new Notebook(productToAdd.getProductId(), productToAdd.getProductName(), productToAdd.getProductPrice(), productQtyA);
                case 4 ->
                        productToAdd = new Crayons(productToAdd.getProductId(), productToAdd.getProductName(), productToAdd.getProductPrice(), productQtyA);
            }
            try {
                AddProductRepository.addProduct(productToAdd);
                productList = InventoryRepository.getAllProducts();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new InvalidInputException("Invalid Input Please Try Again");
        }
    }


    public void buyProduct() throws OutOfStockException, IdNotFoundException {
        System.out.print("Enter Product Id to buy: ");
        int productIdBuy = Integer.parseInt(sc.nextLine());
        boolean isIdExist = productList.stream().anyMatch(product -> productIdBuy == product.getProductId());
        if (!isIdExist) {
            throw new IdNotFoundException("Product Id doesn't exist.");
        }

        System.out.print("How many qty/s you want to purchase: ");
        int productQtyB = Integer.parseInt(sc.nextLine());


        for (Product product : productList) {
            if (productIdBuy == product.getProductId()) {
                if (productQtyB > product.getProductQty()) {
                    throw new OutOfStockException("Out Of Stock !!!");
                }
            }
        }
        productList.stream().filter(product -> productIdBuy == product.getProductId()).forEach(product -> {
            try {
                Payment pay = new Payment(product.getProductId(), product.getProductName(), product.getProductPrice(), productQtyB);
                PaymentRepository.doPayment(pay);
                System.out.println("-----Total-----");
                System.out.println(pay.compute());
                System.out.println("---------------");
                productList = InventoryRepository.getAllProducts();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}


