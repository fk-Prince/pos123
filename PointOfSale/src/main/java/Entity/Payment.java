package Entity;


public class Payment extends Product {

    public Payment(int productId, String productBrandName, double productPrice, int productStock) {
        super(productId, productBrandName, productPrice, productStock);
    }

    public double compute() {
        double tax = 0.12;
        double subTotal = getProductPrice() * getProductQty();
        double taxTotal = subTotal * tax;
        return subTotal + taxTotal;
    }
}
