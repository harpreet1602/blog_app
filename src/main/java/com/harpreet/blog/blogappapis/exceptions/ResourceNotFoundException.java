package com.harpreet.blog.blogappapis.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String fieldName;
    long fieldValue;
    String field;

    public ResourceNotFoundException(String resourceName,String fieldName, long fieldValue){
        super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public ResourceNotFoundException(String resourceName,String fieldName, String field){
        super(String.format("%s not found with %s : %s", resourceName,fieldName,field));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.field = field;
    }

}
