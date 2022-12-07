package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;

@Entity
@Table(name = "buyer")
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Max(value=9999, message="Значение в поле не может быть больше 9999")
    private int loyalty_card;

    @Pattern(regexp = "^[а-яА-Я]+$", message = "Разрешены только буквы кириллицы.")
    @Size(min=2, max=35, message = "Имя слишком длянное или короткое.")
    private String surname;

    @Pattern(regexp = "^[а-яА-Я]+$", message = "Разрешены только буквы кириллицы.")
    @Size(min=2, max=35, message = "Имя слишком длянное или короткое.")
    private String name;

    @Pattern(regexp = "^[а-яА-Я]+$", message = "Разрешены только буквы кириллицы.")
    @Size(min=2, max=35, message = "Имя слишком длянное или короткое.")
    private String middle_name;

    @Email(message = "Email введен некорректно.")
    private String email;

    @OneToMany (mappedBy = "buyer", fetch = FetchType.EAGER)
    private Collection<MakingOrder> makingOrders;



    public Buyer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLoyalty_card() {
        return loyalty_card;
    }

    public void setLoyalty_card(Integer loyalty_card) {
        this.loyalty_card = loyalty_card;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
