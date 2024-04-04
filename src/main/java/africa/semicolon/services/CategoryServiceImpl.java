package africa.semicolon.services;

import africa.semicolon.data.model.Category;
import africa.semicolon.data.model.Note;
import africa.semicolon.data.repositories.CategoryRepository;
import africa.semicolon.data.repositories.NoteRepository;
import africa.semicolon.dtos.requests.CreateCategoryRequest;
import africa.semicolon.dtos.requests.DeleteCategoryRequest;
import africa.semicolon.dtos.requests.EditCategoryRequest;
import africa.semicolon.dtos.responds.CreateCategoryResponse;
import africa.semicolon.dtos.responds.DeleteCategoryResponse;
import africa.semicolon.dtos.responds.EditCategoryResponse;
import africa.semicolon.noteException.BigNoteManagementException;
import africa.semicolon.noteException.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserService userService;

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) {
        String username = createCategoryRequest.getUsername();

        if (!userService.isUserRegistered(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not registered");
        }

        if (!userService.isUserLoggedIn(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not logged in");
        }

        Category category = new Category();
        category.setDescription(createCategoryRequest.getDescription());
        category.setUsername(username);

        Category savedCategory = categoryRepository.save(category);

        CreateCategoryResponse response = new CreateCategoryResponse();
        response.setId(savedCategory.getId());
        response.setDescription(savedCategory.getDescription());
        response.setUsername(savedCategory.getUsername());
        return response;
    }

    @Override
    public Category findCategoryById(String categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        return optionalCategory.orElseThrow(() -> new BigNoteManagementException("Category not found with ID: " + categoryId));
    }


    @Override
    public EditCategoryResponse editCategory(EditCategoryRequest editCategoryRequest) {
        String categoryId = editCategoryRequest.getCategoryId();
        String username = editCategoryRequest.getUsername();

        if (!userService.isUserRegistered(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not registered");
        }

        if (!userService.isUserLoggedIn(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not logged in");
        }

        Category existingCategory = findCategoryById(categoryId);

        existingCategory.setDescription(editCategoryRequest.getDescription());
        existingCategory.setUsername(username);

        Category updatedCategory = categoryRepository.save(existingCategory);

        EditCategoryResponse response = new EditCategoryResponse();
        response.setId(updatedCategory.getId());
        response.setDescription(updatedCategory.getDescription());
        response.setUsername(updatedCategory.getUsername());
        return response;
    }

    @Override
    public DeleteCategoryResponse deleteCategory(DeleteCategoryRequest deleteCategoryRequest) {
        String username = deleteCategoryRequest.getUsername();

        if (!userService.isUserRegistered(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not registered");
        }

        if (!userService.isUserLoggedIn(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not logged in");
        }

        Category existingCategory = findCategoryById(deleteCategoryRequest.getCategoryId());

        if (!existingCategory.getUsername().equals(username)) {
            throw new UserNotFoundException("User with username " + username + " is not authorized to delete this category");
        }

        categoryRepository.delete(existingCategory);
        return new DeleteCategoryResponse();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Note> getNotesByCategoryId(String categoryId) {
        Category category = findCategoryById(categoryId);
        return category.getNotes();
    }

    @Override
    public void addNoteToCategory(String username,String categoryId, String noteId) {
        if (!userService.isUserRegistered(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not registered");
        }

        if (!userService.isUserLoggedIn(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not logged in");
        }

        Category category = findCategoryById(categoryId);
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new BigNoteManagementException("Note not found"));

        category.getNotes().add(note);

        categoryRepository.save(category);
    }

    @Override
    public void removeNoteFromCategory(String username,String categoryId, String noteId) {

        if (!userService.isUserRegistered(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not registered");
        }

        if (!userService.isUserLoggedIn(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not logged in");
        }

        Category category = findCategoryById(categoryId);

        category.getNotes().removeIf(note -> note.getId().equals(noteId));

        categoryRepository.save(category);
    }
}
