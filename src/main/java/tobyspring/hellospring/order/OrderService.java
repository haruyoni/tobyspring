package tobyspring.hellospring.order;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import tobyspring.hellospring.data.OrderRepository;

import java.math.BigDecimal;

public class OrderService {
    private final OrderRepository orderRepository;
    private final JpaTransactionManager jpaTransactionManager;

    public OrderService(OrderRepository orderRepository, JpaTransactionManager jpaTransactionManager) {
        this.orderRepository = orderRepository;
        this.jpaTransactionManager = jpaTransactionManager;
    }

    public Order createOrder(String no, BigDecimal total) {
        Order order = new Order(no, total);

        return new TransactionTemplate(jpaTransactionManager).execute(status -> {
            this.orderRepository.save(order);
            return order;
        });
    }

}
