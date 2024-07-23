package com.haptm.todoapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Size(max = 50)
    @NotNull
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "password_hash")
    private String passwordHash;

    @Size(max = 100)
    @NotNull
    @Email
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Category> categories;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Task> tasks;

    public void userDefault() {
        Category category = new Category("Work");
        categories.add(category);
        Category category2 = new Category("Personal");
        categories.add(category2);
        Category category3 = new Category("Wishlist");
        categories.add(category3);
        Category category4 = new Category("Birthday");
        categories.add(category4);
    }
}
