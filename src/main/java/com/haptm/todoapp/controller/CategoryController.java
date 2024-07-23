package com.haptm.todoapp.controller;

import com.haptm.todoapp.dto.CategoryDto;
import com.haptm.todoapp.model.Category;
import com.haptm.todoapp.service.CategoryService;
import com.haptm.todoapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Category Resource",
        description = "CRUD REST APIs - Create Category, Update Category, Get Category, Get All Categories, Delete Category"
)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("/api/v1/user")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Operation(
            summary = "Get Category REST API",
            description = "Get Category REST API is used to all categories of a user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/{userId}/category")
    public ResponseEntity<List<CategoryDto>> getAllCategories(@Valid @PathVariable Long userId) {
        List<Category> categories = categoryService.getAllCategories(userId);
        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();
        return categoryDtos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categoryDtos);
    }

    @Operation(
            summary = "Create Category REST API",
            description = "Create Category REST API is used to save category in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @PostMapping("/{userId}/category")
    public ResponseEntity<CategoryDto> createCategory(@Valid@PathVariable Long userId, @Valid @RequestBody String categoryName) {
        Category category = categoryService.createCategory(userId, categoryName);
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return ResponseEntity.ok(categoryDto);
    }

    @Operation(
            summary = "Update Category REST API",
            description = "Update Category REST API is used to update category detail in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PutMapping("/{userId}/category")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable Long userId, @Valid @RequestBody CategoryDto categoryDto) {
        Long categoryId = categoryDto.getCategoryId();
        String categoryName = categoryDto.getCategoryName();
        Category category = categoryService.updateCategory(userId, categoryId, categoryName);
        CategoryDto categoryDtoResponse = modelMapper.map(category, CategoryDto.class);
        return ResponseEntity.ok(categoryDtoResponse);
    }

    @Operation(
            summary = "Delete Category REST API",
            description = "Delete Category REST API is used to delete a category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @DeleteMapping("/{userId}/category/{categoryId}")
    public ResponseEntity<String> deleteCategory(@Valid @PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
