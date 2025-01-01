package tobyspring.hellospring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SortTest {
    Sort sort;
    @BeforeEach // 각 테스트를 실행하기 전에 실행되는 메서드
    void beforeEach(){
        // 준비(given)
        sort = new Sort();
        System.out.println(this); // 각 테스트 별로 새로운 인스턴스 생성함(각각 독립적인 실행을 위해)
    }

    @Test
    void sort() {
        // 실행(when)
        List<String> list = sort.sortByLength(Arrays.asList("aa", "b"));

        // 검증(then)
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa"));
        System.out.println("TEST");
    }

    @Test
    void sort3Items(){
        // 실행(when)
        List<String> list = sort.sortByLength(Arrays.asList("aa", "ccc", "b"));

        // 검증(then)
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));
        System.out.println("TEST2");
    }

    @Test
    void sortAlreadySorted(){
        // 실행(when)
        List<String> list = sort.sortByLength(Arrays.asList("b", "aa", "ccc"));

        // 검증(then)
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));
        System.out.println("TEST3");
    }
}
