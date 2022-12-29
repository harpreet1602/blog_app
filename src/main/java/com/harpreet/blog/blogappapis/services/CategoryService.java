package com.harpreet.blog.blogappapis.services;

import com.harpreet.blog.blogappapis.payloads.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
//    create
    CategoryDto createCategory(CategoryDto categoryDto);
//    update
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
//    delete
    public void deleteCategory(Integer categoryId);
//    get
    public CategoryDto getCategory(Integer categoryId);
//    get all
    public List<CategoryDto> getAllCategory();
}