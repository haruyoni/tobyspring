package tobyspring.hellospring.learningtest;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;

public class ClockTest {
    // Clock을 이용해서 LocalDateTime.now 가져오기
    @Test
    void clock(){
        Clock clock = Clock.systemDefaultZone();

        LocalDateTime now = LocalDateTime.now(clock);
        System.out.println(now);

        System.out.println(LocalDateTime.now());
    }


    // Clock을 Test에서 사용할 때 내가 원하는 시간을 지정해서 현재 시간을 가져오게 할 수 있는가?
}
