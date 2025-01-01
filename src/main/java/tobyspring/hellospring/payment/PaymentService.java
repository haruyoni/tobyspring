package tobyspring.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;


public class PaymentService {
    ExRateProvider exRateProvider;

    public PaymentService(ExRateProvider exRateProvider) {
        this.exRateProvider =  exRateProvider;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrentAmount) throws IOException {
        BigDecimal exRate = exRateProvider.getExRate(currency);
        BigDecimal convertedAmount = foreignCurrentAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrentAmount, exRate, convertedAmount, validUntil);
    }


}
