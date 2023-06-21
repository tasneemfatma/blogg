package com.blogg.service.impl;

import com.blogg.entity.Category;
import com.blogg.exception.ResourceNotFoundException;
import com.blogg.payload.CategoryDto;
import com.blogg.repository.CategoryRepository;
import com.blogg.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl implements CategoryService {
@Autowired
private ModelMapper mapper;
@Autowired
private CategoryRepository categoryRepository;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        Category save = categoryRepository.save(category);
      return mapToDto(save);

    }

    @Override
    public void deleteCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("category", "categoryId", categoryId));
        categoryRepository.delete(category);
    }

  Category mapToEntity(CategoryDto categoryDto){
    return mapper.map(categoryDto, Category.class);
    }

   CategoryDto mapToDto(Category category){
      return mapper.map(category,CategoryDto.class);
    }
}
