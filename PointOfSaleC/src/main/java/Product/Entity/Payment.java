package Product.Entity;

public class Payment extends Product {

    public Payment(int productId, String productName, double productPrice, int productQty) {
        super(productId, productName, productPrice, productQty);
    }

    public double compute() {
        double tax = 0.12;
        double subtotal = getProductQty() * getProductPrice();
        double total = subtotal * tax;
        return total + subtotal;
    }
}
