package africa.semicolon.services;

import africa.semicolon.data.model.Category;
import africa.semicolon.data.model.Note;
import africa.semicolon.dtos.requests.CreateCategoryRequest;
import africa.semicolon.dtos.requests.DeleteCategoryRequest;
import africa.semicolon.dtos.requests.EditCategoryRequest;
import africa.semicolon.dtos.responds.CreateCategoryResponse;
import africa.semicolon.dtos.responds.DeleteCategoryResponse;
import africa.semicolon.dtos.responds.EditCategoryResponse;

import java.util.List;

public interface CategoryService {
    CreateCategoryResponse createCategory(CreateCategoryRequest createCategoryRequest);

    EditCategoryResponse editCategory(EditCategoryRequest editCategoryRequest);

    DeleteCategoryResponse deleteCategory(DeleteCategoryRequest deleteCategoryRequest);

    List<Category> getAllCategories();

    List<Note> getNotesByCategoryDescription(String description);

    void addNoteToCategory(String username,String categoryId, Note note);

    void removeNoteFromCategory(String username,String categoryId, Note note);
}
