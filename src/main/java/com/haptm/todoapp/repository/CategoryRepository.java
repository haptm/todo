package com.haptm.todoapp.repository;

import com.haptm.todoapp.model.Category;
import com.haptm.todoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);
    Optional<Category> findByCategoryName(String name);
    Boolean existsByCategoryName(String name);
}
