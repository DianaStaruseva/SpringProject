package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Collection;

@Entity
@Table(name = "order_buyer")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(value = 1, message = "Значение не может быть меньше 1.")
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @OneToMany (mappedBy = "order", fetch = FetchType.EAGER)
    private Collection<MakingOrder> makingOrders;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
