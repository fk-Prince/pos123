package Product.Entity;

import Product.ProductRepository.PaymentRepository;

import java.io.IOException;
import java.util.List;


public class GenerateSales {

    private List<Payment> paymentList;

    public GenerateSales() {
        try {
            this.paymentList = PaymentRepository.getAllPayments();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double computeSales() {
        return paymentList.stream().mapToDouble(Payment::compute).sum();
    }
}
