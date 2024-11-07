package Product.ProductRepository;

import Product.Entity.Payment;

import java.io.*;
import java.util.List;

import static Product.ProductRepository.AddProductRepository.paymentFile;
import static Product.ProductRepository.AddProductRepository.productFile;

public class PaymentRepository {

    public static List<Payment> getAllPayments() throws IOException{
            if (!paymentFile.exists()) paymentFile.createNewFile();
            return new BufferedReader(new FileReader(paymentFile))
                    .lines().map(lines -> lines.split(","))
                    .map(lines -> new Payment(Integer.parseInt(lines[0]), lines[1], Double.parseDouble(lines[2]), Integer.parseInt(lines[3])))
                    .toList();
    }

    public static void doPayment(Payment payment) throws IOException {

        if (!paymentFile.exists()) paymentFile.createNewFile();
        inventory(payment);
        BufferedWriter br = new BufferedWriter(new FileWriter(paymentFile, true));
        br.write(payment.getProductId() + "," + payment.getProductName() + "," + payment.getProductPrice() + "," + payment.getProductQty() + "\n");
        br.close();

    }

    private static void inventory(Payment payment) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(productFile));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            String[] lines = line.split(",");
            if (Integer.parseInt(lines[0]) == payment.getProductId()) {
                int newQty = Integer.parseInt(lines[3]) - payment.getProductQty();

                sb.append(payment.getProductId()).append(",")
                        .append(payment.getProductName()).append(",")
                        .append(payment.getProductPrice()).append(",")
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
}
