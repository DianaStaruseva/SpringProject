package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table(name = "product_pharmacy")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name_product;

    @Max(value=99000, message="Значение в поле не может быть больше 99000")
    @Digits(integer = 5, fraction = 0)
    private  Double price;

    @NotNull(message = "Поле должно быть заполнено.")
    private String expiration_date;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Category category;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Form form;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Manufacturer manufacturer;

    @OneToMany (mappedBy = "product", fetch = FetchType.EAGER)
    private Collection<Order> orders;

    public Product() {
    }

    public Long getId() {
        return id;
    }

//    public Collection<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(Collection<Order> orders) {
//        this.orders = orders;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}
