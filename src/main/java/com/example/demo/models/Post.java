package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "^[а-яА-Я]+$", message = "Разрешены только буквы кириллицы.")
    private String name;

    @Max(value=100000, message="Значение в поле не может быть больше 100000")
    @Digits(integer = 6, fraction = 0)
    private  Double salary;

    //связь с сотрудником
    @ManyToMany(mappedBy = "posts", fetch = FetchType.LAZY)
    private Set<Employee> employees = new HashSet<>();

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Double getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
