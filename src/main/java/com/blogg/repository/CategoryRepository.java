package com.blogg.repository;

import com.blogg.entity.Category;
import com.blogg.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
