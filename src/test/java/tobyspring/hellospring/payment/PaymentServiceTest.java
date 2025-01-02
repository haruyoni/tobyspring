package tobyspring.hellospring.payment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tobyspring.hellospring.exrate.WebApiExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족했는지 검증")
    void prepare() throws IOException {
        testAmount(valueOf(500), valueOf(5_000));
        testAmount(valueOf(1_000), valueOf(10_000));
        testAmount(valueOf(3_000), valueOf(30_000));

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

    private static Payment testAmount(BigDecimal exRate, BigDecimal convertedAmount) throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate));

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // 환율 정보 가져온다
        assertThat(payment.getExRate()).isEqualTo(exRate);

        // 원화 환산 금액 계산
        assertThat(payment.getConvertedAmount()).isEqualTo(convertedAmount); // _ : 자릿수 구별 문자
        return payment;
    }
}