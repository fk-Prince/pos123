package Entity;

import Repository.TotalSalesService;

import java.util.List;

public class GenerateSales {
    private final List<Payment> getAllPayments;

    public GenerateSales() {
        getAllPayments = new TotalSalesService().getOverallSales();
    }


    public double computeSales() {
        return getAllPayments.stream().mapToDouble(Payment::compute).sum();
    }
}
