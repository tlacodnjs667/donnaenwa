package com.wooruk.donnaenwa.controller;

import com.wooruk.donnaenwa.domain.entity.Category;
import com.wooruk.donnaenwa.domain.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("category")
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<Category>> getCategories () {
    List<Category> categories = categoryService.getCategories();
    return ResponseEntity.ok(categories);
  }
}
