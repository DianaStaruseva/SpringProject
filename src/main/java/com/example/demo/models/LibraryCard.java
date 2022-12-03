package com.example.demo.models;

import org.springframework.security.access.prepost.PreAuthorize;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "library_cards")
public class LibraryCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String number;
    @OneToOne(mappedBy = "library_card")
    private Reader owner;

    public LibraryCard(String number) {
        this.number = number;
    }

    public LibraryCard() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Reader getOwner() {
        return owner;
    }

    public void setOwner(Reader owner) {
        this.owner = owner;
    }
}
