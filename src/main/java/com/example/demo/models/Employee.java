package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    //связь с должностью
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "employee_post",
            joinColumns = {
                    @JoinColumn(name = "employee_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "post_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Post> posts = new HashSet<>();

    public void addPost(Post post){
        this.posts.add(post);
    }

    public void removePost(Post post){
        this.posts.remove(post);
    }

    public Employee() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
