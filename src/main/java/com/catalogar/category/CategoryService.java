package com.catalogar.category;

import com.catalogar.exception.ResourceNotFoundException;
import com.catalogar.exception.UniqueFieldConflictException;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Category getById(UUID id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoria com id: " + id + " não encontrada"
                ));
    }

    public Category create(CategoryRequest categoryRequest) {
        boolean existsByName = categoryRepository
                .existsByName(categoryRequest.name());

        if (existsByName) {
            throw new UniqueFieldConflictException(
                    "Categoria com o nome " + categoryRequest.name() + " já está cadastrada"
            );
        }

        Category category = new Category(
                categoryRequest.name(),
                categoryRequest.color(),
                categoryRequest.backgroundColor()
        );

        return categoryRepository.save(category);
    }

    public Category update(UUID id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoria com id: " + id + " não encontrada"
                ));

        category.setName(categoryRequest.name());
        category.setColor(categoryRequest.color());
        category.setBackgroundColor(categoryRequest.backgroundColor());

        return categoryRepository.save(category);
    }

    public void delete(UUID id) {
        boolean existsById = categoryRepository.existsById(id);

        if (!existsById) {
            throw new ResourceNotFoundException(
                    "Categoria com id: " + id + " não encontrada"
            );
        }

        categoryRepository.deleteById(id);
    }
}
