package com.harpreet.blog.blogappapis.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
public class Role {
    @Id
    private int id;
    private String name;
}
