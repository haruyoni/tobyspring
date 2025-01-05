package tobyspring.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;


public class PaymentService {
    private final ExRateProvider exRateProvider;
    private final Clock clock;


    public PaymentService(ExRateProvider exRateProvider, Clock clock) {
        this.exRateProvider = exRateProvider;
        this.clock = clock;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrentAmount) throws IOException {
        BigDecimal exRate = exRateProvider.getExRate(currency);
        BigDecimal convertedAmount = foreignCurrentAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now(clock).plusMinutes(30); // Bean으로 만든 clock의 now 값을 가져오게 변경

        return new Payment(orderId, currency, foreignCurrentAmount, exRate, convertedAmount, validUntil);
    }


}
