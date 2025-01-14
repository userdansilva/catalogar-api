package com.catalogar.category;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getById(UUID id) {
        return categoryRepository.findById(id);
    }

    public Category create(NewCategoryRequest category) {
        Category newCategory = new Category();

        newCategory.setName(category.name());
        newCategory.setColor(category.color());
        newCategory.setBackgroundColor(category.backgroundColor());

        return categoryRepository.save(newCategory);
    }

    public Category update(UUID id, Category  category) {
        Category updatedCategory = new Category();

        updatedCategory.setId(id);
        updatedCategory.setName(category.getName());
        updatedCategory.setColor(category.getColor());
        updatedCategory.setBackgroundColor(category.getBackgroundColor());

        return categoryRepository.save(updatedCategory);
    }

    public void delete(UUID id) {
        categoryRepository.deleteById(id);
    }
}
