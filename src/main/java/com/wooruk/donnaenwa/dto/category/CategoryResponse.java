package com.wooruk.donnaenwa.dto.category;

import com.wooruk.donnaenwa.domain.entity.Category;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CategoryResponse {
  private List<Category> categories;
}
