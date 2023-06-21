package com.blogg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="categories", uniqueConstraints=@UniqueConstraint(columnNames={"title"}))
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int categoryId;
    private String title;

    @OneToMany(mappedBy="category", cascade=CascadeType.ALL)
    private Set<Post> posts;
}
