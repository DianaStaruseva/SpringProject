package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;


@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "^[а-яА-Я]+$", message = "Разрешены только буквы кириллицы.")
    @Size(min=2, max=35, message = "Имя слишком длянное или короткое.")
    private String name;

    @Pattern(regexp = "^[а-яА-Я]+$", message = "Разрешены только буквы кириллицы.")
    private String patronymic;

    @Size(min=1, max=50, message = "Фамилия слишком длянная или короткая.")
    @Pattern(regexp = "^[а-яА-Я]+$", message = "Разрешены только буквы кириллицы.")
    private String surname;

    @Min(value=10, message = "Возраст не может быть меньше 10.")
    private  Integer age;

    @Min(value=1, message = "Колличество работ не может быть меньше 1.")
    private  Integer number_of_works;

    @OneToMany (mappedBy = "author", fetch = FetchType.EAGER)
    private Collection<Book> books;

    public Author() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getNumber_of_works() {
        return number_of_works;
    }

    public void setNumber_of_works(Integer number_of_works) {
        this.number_of_works = number_of_works;
    }

    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }
}
