package com.harpreet.blog.blogappapis.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;
    @NotBlank
    @Size(min = 4,message="Category Title must be greater than 4 characters")
    private String categoryTitle;
    @NotBlank
    @Size(min = 10, message="Category Description must be greater than 10 characters")
    private String categoryDescription;
}
