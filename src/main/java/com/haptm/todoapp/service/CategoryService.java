package com.haptm.todoapp.service;

import com.haptm.todoapp.exception.CategoryExistException;
import com.haptm.todoapp.exception.CategoryNotFoundException;
import com.haptm.todoapp.exception.UserNotFoundException;
import com.haptm.todoapp.model.Category;
import com.haptm.todoapp.model.User;
import com.haptm.todoapp.repository.CategoryRepository;
import com.haptm.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private User checkIfUserExists(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }

    public List<Category> getAllCategories(Long userId) {
        return categoryRepository.findByUser(checkIfUserExists(userId));
    }

    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        }
        else {
            throw new CategoryNotFoundException("Category with id " + id + " not found");
        }
    }

    public Category getCategoryByName(String categoryName) {
        Optional<Category> category = categoryRepository.findByCategoryName(categoryName);
        if (category.isPresent()) {
            return category.get();
        }
        else {
            throw new CategoryNotFoundException("Category with name " + categoryName + " not found");
        }
    }

    public Category createCategory(Long userId, String categoryName) {
        User user = checkIfUserExists(userId);
        List<Category> categories = categoryRepository.findByUser(user);
        for (Category category : categories) {
            if(category.getCategoryName().equals(categoryName)) {
                throw new CategoryExistException("Category already exists");
            }
        }
        Category category = new Category(categoryName);
        category.setUser(user);
        return  categoryRepository.save(category);
    }

    public Category updateCategory(Long userId, Long categoryId, String categoryNameNew) {
        User user = checkIfUserExists(userId);
        List<Category> categories = categoryRepository.findByUser(user);
        for (Category category : categories) {
            if(category.getCategoryName().equals(categoryNameNew)) {
                throw new CategoryExistException("Category already exists");
            }
        }
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setCategoryName(categoryNameNew);
            return categoryRepository.save(category);
        }
        else {
             throw new CategoryNotFoundException("Category with id " + categoryId + " not found");
        }
    }

    public void deleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            categoryRepository.deleteById(categoryId);
        }
        else {
            throw new CategoryNotFoundException("Category with id " + categoryId + " not found");
        }
    }
}
