package tobyspring.hellospring;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import tobyspring.hellospring.data.OrderRepository;
import tobyspring.hellospring.order.OrderService;

@Configuration
@Import(DataConfig.class) //DataConfig에 있는 설정도 가져올 수 있음
public class OrderConfig {
    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository();
    }

    @Bean
    public OrderService orderService(JpaTransactionManager transactionManager) {
        return new OrderService(orderRepository(), transactionManager);
    }

}
