package com.harpreet.blog.blogappapis.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.harpreet.blog.blogappapis.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min = 4, max=20, message = "Username must be of minimum 4 characters and maximum of 20 characters!")
    private String name;
    @Email(message = "Email address is not valid!")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty
    @Size(min = 4, max=20, message = "Password must be of minimum of 4 characters and maximum of 20 characters!")
    private String password;
    @NotEmpty
    private String about;
    private Set<RoleDto> roles = new HashSet<>();
    @JsonIgnore
    public String getPassword(){
        return this.password;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
