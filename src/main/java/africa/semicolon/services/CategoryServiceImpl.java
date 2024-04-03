package africa.semicolon.services;

import africa.semicolon.data.model.Category;
import africa.semicolon.data.repositories.CategoryRepository;
import africa.semicolon.noteException.BigNoteManagementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findCategoryById(String categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        return optionalCategory.orElseThrow(() -> new BigNoteManagementException("Category not found with ID: " + categoryId));
    }


    @Override
    public Category updateCategory(String categoryId, Category category) {
        Category existingCategory = findCategoryById(categoryId);
        existingCategory.setUsername(category.getUsername());
        existingCategory.setDescription(category.getDescription());
        return categoryRepository.save(existingCategory);


    }

    @Override
    public void deleteCategory(String categoryId) {
        Category existingCategory = findCategoryById(categoryId);
        if (existingCategory != null) {
            categoryRepository.delete(existingCategory);


        }

    }

    @Override
    public List<Category> getAllCategories(){
            return categoryRepository.findAll();
        }

}
