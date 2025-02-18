package com.wooruk.donnaenwa.domain.repository;

import com.wooruk.donnaenwa.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
