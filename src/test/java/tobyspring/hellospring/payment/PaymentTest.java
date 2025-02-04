package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tobyspring.hellospring.api.ApiTemplate;
import tobyspring.hellospring.exrate.ExRateProvider;
import tobyspring.hellospring.exrate.WebApiExRateProvider;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class PaymentTest {
    @Test
    void createPrepared() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        ExRateProvider exRateProvider = new ExRateProviderStub(BigDecimal.valueOf(1_000));

        Payment payment = Payment.createPrepared(1L, "USD", BigDecimal.TEN, exRateProvider, LocalDateTime.now(clock));

        Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(10_000));
        Assertions.assertThat(payment.getValidUntil()).isEqualTo(LocalDateTime.now(clock).plusMinutes(30));
    }

    @Test
    void isValid() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Payment payment = Payment.createPrepared(1L, "USD", BigDecimal.TEN, new WebApiExRateProvider(new ApiTemplate()), LocalDateTime.now(clock));

        Assertions.assertThat(payment.isValid(clock)).isTrue();
        Assertions.assertThat(payment.isValid(Clock.offset(clock, Duration.of(30, ChronoUnit.MINUTES)))).isFalse();


    }
}
