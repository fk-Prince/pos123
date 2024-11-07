package Product.Entity;

public class Product {

    private int productId;
    private String productName;
    private double productPrice;
    private int productQty;

    public Product(int productId, String productName, double productPrice, int productQty) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQty = productQty;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductQty() {
        return productQty;
    }

    public void displayDetails() {
        System.out.println("-------------------------------");
        System.out.println("Product Id: " + productId);
        System.out.println("Product Name: " + productName);
        System.out.println("Product Price: " + productPrice);
        System.out.println("Product Quantity: " + productQty);
    }
}
