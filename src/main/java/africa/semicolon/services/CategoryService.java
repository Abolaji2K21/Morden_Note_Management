package africa.semicolon.services;

import africa.semicolon.data.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category findCategoryById(String categoryId);
    Category updateCategory(String categoryId, Category category);
    void deleteCategory(String categoryId);
    List<Category> getAllCategories();

}
