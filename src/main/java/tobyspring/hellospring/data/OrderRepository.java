package tobyspring.hellospring.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.context.annotation.Bean;
import tobyspring.hellospring.order.Order;

import java.math.BigDecimal;

public class OrderRepository {
    private final EntityManagerFactory emf;

    public OrderRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Order order) {
        // em
        EntityManager em = emf.createEntityManager();

        // transaction
        /*
        em.getTransaction()이 계속 중복되니까 변수로 담아서 사용하고 싶다
        => 그 부분을 긁어서 리팩터 - introduce Variable 선택하면
        아래처럼 변수로 담아서 다 바꿔줌
         */
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try{ // em.persist
            em.persist(order);
            em.flush();

            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction.isActive()) transaction.rollback();
            throw e;
        } finally {
            if(em.isOpen()) em.close();
        }
        
        em.close();
    }

}
