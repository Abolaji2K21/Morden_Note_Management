package africa.semicolon.services;

import java.util.List;
import java.util.Optional;

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

import static africa.semicolon.utils.Mapper.mapToCreateCategoryResponse;
import static africa.semicolon.utils.Mapper.mapToEditCategoryResponse;

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
        validateUser(username);

        Category category = new Category();
        category.setDescription(createCategoryRequest.getDescription());
        category.setUsername(username);

        Category savedCategory = categoryRepository.save(category);

        Note note = new Note();
        note.setTitle(note.getTitle());
        note.setContent(note.getContent());
        note.setCategory(savedCategory);

        noteRepository.save(note);


        return mapToCreateCategoryResponse(savedCategory);
    }

    @Override
    public EditCategoryResponse editCategory(EditCategoryRequest editCategoryRequest) {
        String description = editCategoryRequest.getDescription();
        String username = editCategoryRequest.getUsername();
        validateUser(username);

        Category existingCategory = getCategoryByDescription(description);

        existingCategory.setUsername(username);

        if (!existingCategory.getDescription().equals(description)) {
            existingCategory.setDescription(description);
        }
        if (!existingCategory.getUsername().equals(username)) {
            throw new UserNotFoundException("User with username " + username + " is not authorized to edit this category");
        }

        Category updatedCategory = categoryRepository.save(existingCategory);

        return mapToEditCategoryResponse(updatedCategory);
    }

    @Override
    public DeleteCategoryResponse deleteCategory(DeleteCategoryRequest deleteCategoryRequest) {
        String username = deleteCategoryRequest.getUsername();
        validateUser(username);

        Category existingCategory = getCategoryByDescription(deleteCategoryRequest.getDescription());

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
    public List<Note> getNotesByCategoryDescription(String description) {
        Category category = getCategoryByDescription(description);
        return category.getNotes();
    }

    public void addNoteToCategory(String username, String description, Note note) {
        validateUser(username);

        Category category = getCategoryByDescription(description);

        if (note.getId() == null) {
            note = noteRepository.save(note);
        }
//        note.setCategory(category);
        category.getNotes().add(note);

        categoryRepository.save(category);
    }

    @Override
    public void removeNoteFromCategory(String username, String description, Note note) {
        validateUser(username);

        Category category = getCategoryByDescription(description);

        category.getNotes().removeIf(n -> n.getId().equals(note.getId()));

        categoryRepository.save(category);
    }

    private void validateUser(String username) {
        if (!userService.isUserRegistered(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not registered");
        }

        if (!userService.isUserLoggedIn(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not logged in");
        }
    }

//    private Category getCategoryById(String categoryId) {
//        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
//        return optionalCategory.orElseThrow(() -> new BigNoteManagementException("Category not found with ID: " + categoryId));
//    }

    private Category getCategoryByDescription(String description) {
        Optional<Category> optionalCategory = categoryRepository.findByDescription(description);
        return optionalCategory.orElseThrow(() -> new BigNoteManagementException("Category not found with description: " + description));
    }

//    private Note getNoteById(String noteId) {
//        return noteRepository.findById(noteId)
//                .orElseThrow(() -> new BigNoteManagementException("Note not found with ID: " + noteId));
//    }

}
