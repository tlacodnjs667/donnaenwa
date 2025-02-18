package com.wooruk.donnaenwa.domain.service.category;

import com.wooruk.donnaenwa.domain.entity.Category;
import com.wooruk.donnaenwa.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;

  @Override
  public List<Category> getCategories() {
    return categoryRepository.findAll();
  }
}
