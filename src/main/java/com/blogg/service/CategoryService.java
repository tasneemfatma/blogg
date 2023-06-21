package com.blogg.service;

import com.blogg.payload.CategoryDto;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    void deleteCategory(int categoryId);
}
