package tobyspring.hellospring.order;

import jakarta.persistence.*;

import java.math.BigDecimal;
//
//@Entity
//@Table(name = "orders")
public class Order {
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String no;

    private BigDecimal total;

    public Order() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order(String no, BigDecimal total) {
        this.no = no;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", no='" + no + '\'' +
                ", total=" + total +
                '}';
    }

    public String getNo() {
        return no;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
