package tobyspring.hellospring.payment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tobyspring.hellospring.exrate.WebApiExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {
    Clock clock;

    @BeforeEach
    void beforeEach(){
        this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족했는지 검증")
    void convertedAmount() {

        testAmount(valueOf(500), valueOf(5_000), this.clock);
        testAmount(valueOf(1_000), valueOf(10_000), clock);
        testAmount(valueOf(3_000), valueOf(30_000), clock);

        // 원화 환산 금액의 유효시간 계산
//        assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
//        assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));

        /*
        위 테스트 코드 문제점
        - 외부 환경 오류
        - exrate에서 받아온걸로 계산한게 맞는지 확실x -> Stub(대역)을 만들어서 검증
        - 유효시간 검증 짜침
         */
    }

    @Test
    void validUntil() {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(valueOf(1_000)), clock);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // valid until이 prepare() 30분 뒤로 설정 됐는가?
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }

    private static Payment testAmount(BigDecimal exRate, BigDecimal convertedAmount, Clock clock) {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // 환율 정보 가져온다
        assertThat(payment.getExRate()).isEqualByComparingTo(exRate); // isEqualTo는 자릿수까지 정확히 같은지 비교하기 때문에 오류 가능성이 큼 compareTo를 사용하는 것이 좋다

        // 원화 환산 금액 계산
        assertThat(payment.getConvertedAmount()).isEqualTo(convertedAmount); // _ : 자릿수 구별 문자
        return payment;
    }
}