package com.harpreet.blog.blogappapis.entities;
import lombok.Data;
import javax.persistence.*;
@Entity
@Data
public class Role {
    @Id
    private int id;
    private String name;
}