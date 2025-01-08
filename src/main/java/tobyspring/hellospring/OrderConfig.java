package tobyspring.hellospring;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import tobyspring.hellospring.data.JdbcOrderRepository;
import tobyspring.hellospring.order.OrderRepository;
import tobyspring.hellospring.order.OrderService;

import javax.sql.DataSource;

@Configuration
@Import(DataConfig.class) //DataConfig에 있는 설정도 가져올 수 있음
public class OrderConfig {
    @Bean
    public OrderRepository orderRepository(DataSource dataSource) {
        return new JdbcOrderRepository(dataSource);
    }

    @Bean
    public OrderService orderService(PlatformTransactionManager transactionManager, OrderRepository orderRepository) {
        return new OrderService(orderRepository, transactionManager);
    }

}
