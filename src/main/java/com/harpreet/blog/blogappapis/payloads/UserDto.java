package com.harpreet.blog.blogappapis.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    private String name;
    private String email;
    private String password;
    private String about;
}